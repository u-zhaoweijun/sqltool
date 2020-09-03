package cn.tenmg.sqltool.exception;

public class DataAccessException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5819754179912275832L;

	public DataAccessException() {
		super();
	}

	public DataAccessException(String massage) {
		super(massage);
	}

	public DataAccessException(Throwable cause) {
		super(cause);
	}

	public DataAccessException(String massage, Throwable cause) {
		super(massage, cause);
	}
}
