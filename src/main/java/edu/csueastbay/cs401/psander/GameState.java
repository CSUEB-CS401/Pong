package edu.csueastbay.cs401.psander;

/**
 * Objects that represents all of the state in the game
 * that needs to persist between scenes.
 */
public class GameState {
    private int _winningScore;
    private int _player1Score;
    private int _player2Score;
    private int _winningPlayer;

    /**
     * Resets the data to its initial state.
     */
    public void reset() {
        _winningScore = 0;
        _player1Score = 0;
        _player2Score = 0;
        _winningPlayer = 0;
    }

    public int get_winningScore() {
        return _winningScore;
    }

    public void set_winningScore(int _winningScore) {
        this._winningScore = _winningScore;
    }

    public int get_player1Score() {
        return _player1Score;
    }

    public void set_player1Score(int _player1Score) {
        this._player1Score = _player1Score;
    }

    public int get_player2Score() {
        return _player2Score;
    }

    public void set_player2Score(int _player2Score) {
        this._player2Score = _player2Score;
    }

    public int get_winningPlayer() {
        return _winningPlayer;
    }

    public void set_winningPlayer(int _winningPlayer) {
        this._winningPlayer = _winningPlayer;
    }
}
