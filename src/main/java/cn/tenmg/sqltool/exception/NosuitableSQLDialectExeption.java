package cn.tenmg.sqltool.exception;

public class NosuitableSQLDialectExeption extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1096220289612512140L;

	public NosuitableSQLDialectExeption() {
		super();
	}

	public NosuitableSQLDialectExeption(String massage) {
		super(massage);
	}

	public NosuitableSQLDialectExeption(Throwable cause) {
		super(cause);
	}

	public NosuitableSQLDialectExeption(String massage, Throwable cause) {
		super(massage, cause);
	}
}
