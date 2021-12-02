package edu.csueastbay.cs401.psander.engine.input;

import javafx.scene.input.KeyCode;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;


/**
 * The InputManager is responsible for consuming keyboard inputs from
 * JavaFX and translating them into in-game inputs.
 */
public class InputManager {

    private static InputManager _instance = null;

    private final Hashtable<KeyCode, KeyStatus> _keyboardStates = new Hashtable<>();
    private final Hashtable<InputEvent, KeyStatus> _inputStates = new Hashtable<>();
    private final Hashtable<KeyCode, InputEvent> _keycodeToInputMapping = new Hashtable<>();
    private final Hashtable<InputEvent, Optional<KeyCode>> _inputToKeycodeMapping = new Hashtable<>();
    private final List<Pair<KeyCode, KeyStatus>> _keyEventQueue;
    private final Hashtable<KeyCode, MenuInputEvent> _keyToMenuMapping = new Hashtable<>();
    private final Hashtable<MenuInputEvent, InputState> _menuInputStates = new Hashtable<>();

    private boolean _capturingKeycode = false;
    private Optional<KeyCode> _capturedKeycode;

    private InputManager() {
        _keyEventQueue = Collections.synchronizedList(new ArrayList<>());

        setDefaultInputBindings();
    }

    /**
     * Reinitialize the input manager. Required because this is implemented as a singleton.
     */
    public void init() {
        setDefaultInputBindings();
        _keyboardStates.clear();
        _inputStates.clear();
        _menuInputStates.clear();
        synchronized (_keyEventQueue) {
            _capturingKeycode = false;
            _capturedKeycode = Optional.empty();
            _keyEventQueue.clear();
        }
    }

    /**
     * Retrieves the current singleton instance of the InputManager.
     * @return The current instance.
     */
    public static InputManager getInstance()
    {
        if (_instance == null)
            _instance = new InputManager();

        return _instance;
    }


    /**
     * Processes all received input to make it available for querying.
     * @param delta The amount of time elapsed since the last frame.
     */
    public void update(double delta)
    {
        List<Pair<KeyCode, KeyStatus>> events;
        var processingMap = new Hashtable<KeyCode, KeyStatus>();
        synchronized(_keyEventQueue)
        {
            events = new ArrayList<>(_keyEventQueue);
            _keyEventQueue.clear();
        }

        for(var event : events) {
            var key = event.getKey();
            var status = event.getValue();

            // We just want to process the _last_ update we received in a frame
            // (Ignoring the very small chance of dropped inputs for now.)
            processingMap.put(key, status);
        }

        _keyboardStates.putAll(processingMap);

        // Update menu inputs - they have different logic to avoid
        // key repeats

        // First we do updates based on the current state, so we don't
        // accidentally update a state that's newly-changed from a keyboard
        // input
        _menuInputStates.forEach((event, oldState) ->{
            InputState newState;
            switch(oldState) {

                case UNPRESSED, RELEASED -> newState = InputState.UNPRESSED;
                case PRESSED, HELD -> newState = InputState.HELD;
                default -> throw new IllegalStateException("Unexpected value: " + oldState);
            }
            _menuInputStates.put(event, newState);
        });

        processingMap.forEach( (kc, newStatus) -> {
            if (!_keyToMenuMapping.containsKey(kc)) return;

            var menuInput = _keyToMenuMapping.get(kc);
            if (!_menuInputStates.containsKey(menuInput))
                _menuInputStates.put(menuInput, InputState.UNPRESSED);

            InputState newState;
            var oldState = _menuInputStates.get(menuInput);
            // If-switch is kind of silly, but I don't have time to look up
            // if there's nice pattern matching support (and Java doesn't have
            // tuples anyway, as far as I've been able to check.)
            if (newStatus == KeyStatus.KEY_DOWN) {
                switch(oldState) {
                    case UNPRESSED, RELEASED -> newState = InputState.PRESSED;
                    case PRESSED, HELD -> newState = InputState.HELD;
                    default -> throw new IllegalStateException("Unexpected value: " + oldState);
                }
            } else { // Key Up state
                switch(oldState) {
                    case UNPRESSED, RELEASED -> newState = InputState.UNPRESSED;
                    case PRESSED, HELD -> newState = InputState.RELEASED;
                    default -> throw new IllegalStateException("Unexpected value: " + oldState);
                }
            }
            _menuInputStates.put(menuInput, newState);
        });


        _keycodeToInputMapping.forEach( (code, event) -> {
            if (!_keyboardStates.containsKey(code)) return;

            var state = _keyboardStates.get(code);
            _inputStates.put(event, state);
        });
    }


    /**
     * Queries if a given key is currently being pressed or not.
     * @param k The keycode to check.
     * @return True if the key is currently being pressed, otherwise false.
     */
    public boolean isKeyPressed(KeyCode k)
    {
        if (!_keyboardStates.containsKey(k)) return false;

        var state = _keyboardStates.get(k);
        return state == KeyStatus.KEY_DOWN;
    }


    /**
     * Determines if the given input is being pressed or not.
     * @param event The input to check.
     * @return True if it is being pressed, otherwise false.
     */
    public boolean isInputActive(InputEvent event) {
        if (!_inputStates.containsKey(event)) return false;

        var state = _inputStates.get(event);
        return state == KeyStatus.KEY_DOWN;
    }

    /**
     * Checks if a particular menu event was pressed or not. Only returns
     * true on the frame the input was first received, to avoid key repeats.
     * @param menuInput The menu event to check for.
     * @return True if the event was just input, otherwise false.
     */
    public boolean isMenuInputPressed(MenuInputEvent menuInput) {
        if (!_menuInputStates.containsKey(menuInput))
            _menuInputStates.put(menuInput, InputState.UNPRESSED);
        var state = _menuInputStates.get(menuInput);

        return state == InputState.PRESSED;
    }

    /**
     * Returns the keycode for an associated input event.
     * @param input The input to retrieve the keycode for.
     * @return An optional that contains the keycode, if available, empty
     * if no keycode is set for that input.
     */
    public Optional<KeyCode> getKeycodeForInput(InputEvent input) {
        return _inputToKeycodeMapping.get(input);
    }

    /**
     * Sets the keycode for the indicated input event. If another input event
     * is using that keycode it will be removed from that one to be assigned
     * to the new one.
     * @param input The input event to set the keycode for.
     * @param keycode The keycode to set.
     */
    public void setKeycodeForInput(InputEvent input, KeyCode keycode) {
        // If not already in use, we can set it and leave
        if (!_keycodeToInputMapping.containsKey(keycode)) {
            _keycodeToInputMapping.put(keycode, input);
            _inputToKeycodeMapping.put(input, Optional.of(keycode));
            return;
        }

        // Here it is in use, first check if it already belongs to the input
        if (_keycodeToInputMapping.get(keycode) == input) return; // Nothing to do if they match

        // Otherwise we first blank whoever is using it, then set the new owner of the input.
        var oldInput = _keycodeToInputMapping.get(keycode);
        _inputToKeycodeMapping.put(oldInput, Optional.empty());

        var oldKeycode = _inputToKeycodeMapping.get(input);
        _keycodeToInputMapping.remove(oldKeycode);

        _keycodeToInputMapping.put(keycode, input);
        _inputToKeycodeMapping.put(input, Optional.of(keycode));
    }

    /**
     * Asks the InputManager to capture the next key down event that occurs.
     */
    public void captureNextKeycode() {
        synchronized (_keyEventQueue) {
            _capturingKeycode = true;
        }
    }

    /**
     * Retrieves the captured key stroke after calling captureNextKeycode();
     * Will return an empty optional if nothing has been captured, and after
     * returning a successful capture will also be wiped.
     * @return An optional containing the captured keycode if one was captured,
     * otherwise returns an empty optional.
     */
    public Optional<KeyCode> getCapturedKeystroke() {
        synchronized(_keyEventQueue){
            var temp = _capturedKeycode;
            _capturedKeycode = Optional.empty();
            return temp;
        }
    }

    /**
     * Processes a key up event.
     * @param k The keycode to process.
     */
    public void keyUp(KeyCode k)
    {
        synchronized (_keyEventQueue) {
            _keyEventQueue.add(new Pair<>(k, KeyStatus.KEY_UP));
        }
    }

    /**
     * Processes a key down event.
     * @param k The keycode to process.
     */
    public void keyDown(KeyCode k)
    {
        synchronized (_keyEventQueue) {
            if (_capturingKeycode) {
                _capturingKeycode = false;
                _capturedKeycode = Optional.of(k);
            }
            _keyEventQueue.add(new Pair<>(k, KeyStatus.KEY_DOWN));
        }
    }

    private void setDefaultInputBindings() {
        // Menu key bindings.
        _keycodeToInputMapping.clear();
        _keyToMenuMapping.put(KeyCode.UP,       MenuInputEvent.MENU_UP);
        _keyToMenuMapping.put(KeyCode.DOWN,     MenuInputEvent.MENU_DOWN);
        _keyToMenuMapping.put(KeyCode.LEFT,     MenuInputEvent.MENU_LEFT);
        _keyToMenuMapping.put(KeyCode.RIGHT,    MenuInputEvent.MENU_RIGHT);
        _keyToMenuMapping.put(KeyCode.SPACE,    MenuInputEvent.MENU_CONFIRM);
        _keyToMenuMapping.put(KeyCode.ENTER,    MenuInputEvent.MENU_CONFIRM);
        _keyToMenuMapping.put(KeyCode.ESCAPE,   MenuInputEvent.MENU_CANCEL);

        // Default input mappings
        _keycodeToInputMapping.clear();
        _inputToKeycodeMapping.clear();
        _keycodeToInputMapping.put(KeyCode.W,     InputEvent.PLAYER_1_UP);
        _keycodeToInputMapping.put(KeyCode.S,     InputEvent.PLAYER_1_DOWN);
        _keycodeToInputMapping.put(KeyCode.A,     InputEvent.PLAYER_1_LEFT);
        _keycodeToInputMapping.put(KeyCode.D,     InputEvent.PLAYER_1_RIGHT);
        _keycodeToInputMapping.put(KeyCode.UP,    InputEvent.PLAYER_2_UP);
        _keycodeToInputMapping.put(KeyCode.DOWN,  InputEvent.PLAYER_2_DOWN);
        _keycodeToInputMapping.put(KeyCode.LEFT,  InputEvent.PLAYER_2_LEFT);
        _keycodeToInputMapping.put(KeyCode.RIGHT, InputEvent.PLAYER_2_RIGHT);
        _keycodeToInputMapping.forEach( (keycode, input) -> _inputToKeycodeMapping.put(input, Optional.of(keycode)));
    }

    private enum KeyStatus {
        KEY_UP,
        KEY_DOWN
    }
}
