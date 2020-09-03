package cn.tenmg.sqltool.exception;

public class PkNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8711952328852873077L;

	public PkNotFoundException() {
		super();
	}

	public PkNotFoundException(String massage) {
		super(massage);
	}

	public PkNotFoundException(Throwable cause) {
		super(cause);
	}

	public PkNotFoundException(String massage, Throwable cause) {
		super(massage, cause);
	}
}
