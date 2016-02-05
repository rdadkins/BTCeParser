package co.bitsquared.btceparser.core.data;

public enum DepthType {

	/**
	 * People who are buying.
	 */
	BID,

	/**
	 * People who are selling.
	 */
	ASK;

	@Override
	public String toString() {
		return name().toLowerCase();
	}
}
