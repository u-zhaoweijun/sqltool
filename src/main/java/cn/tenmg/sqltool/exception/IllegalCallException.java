package cn.tenmg.sqltool.exception;

public class IllegalCallException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6136502662630490330L;

	public IllegalCallException() {
		super();
	}

	public IllegalCallException(String massage) {
		super(massage);
	}

	public IllegalCallException(Throwable cause) {
		super(cause);
	}

	public IllegalCallException(String massage, Throwable cause) {
		super(massage, cause);
	}
}
