package cn.tenmg.sqltool.exception;

public class ColumnNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1049475731838144594L;
	
	public ColumnNotFoundException() {
		super();
	}

	public ColumnNotFoundException(String massage) {
		super(massage);
	}

	public ColumnNotFoundException(Throwable cause) {
		super(cause);
	}

	public ColumnNotFoundException(String massage, Throwable cause) {
		super(massage, cause);
	}
}
