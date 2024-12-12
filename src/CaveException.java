
public class CaveException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private boolean kritine;

	public CaveException(String msg, boolean kritine) {
		super(msg);
		this.kritine = kritine;
	}

	public boolean isKritine() {
		return kritine;
	}

}
