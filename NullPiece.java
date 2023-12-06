public class NullPiece extends ChessPiece {
	private char symbol = 'O';

	public NullPiece(int x, int y, char ps) {
		super(x, y, ps);
	}

	public char GetSymbol() {
		return symbol;
	}

	public void Move(int x, int y) {
		System.out.println("Test");
	}
}