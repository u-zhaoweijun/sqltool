package cn.tenmg.sqltool.exception;

public class TransactionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2081897494890685994L;

	public TransactionException() {
		super();
	}

	public TransactionException(String massage) {
		super(massage);
	}

	public TransactionException(Throwable cause) {
		super(cause);
	}

	public TransactionException(String massage, Throwable cause) {
		super(massage, cause);
	}
}
