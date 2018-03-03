package idwall.desafio.string;

/**
 * Created by Rodrigo Catão Araujo on 06/02/2018.
 */
public abstract class StringFormatter {

	private Integer limit;
	private boolean justify;

	/**
	 * Default Constructor containing default values.
	 */
	public StringFormatter() {
		this.limit = 40;
		this.justify = true;
	}

	/**
	 * It receives a text and should format this text
	 * 
	 * @param text
	 *            A String
	 * @param limit
	 *            Defines the limit of characteres per line
	 * @param justify
	 *            Flag that tells if it should identify the text or not
	 * @return formatted text
	 */
	public abstract String format(String text, Integer limit, boolean justify);

	/**
	 * It returns the <code>limit</code>
	 * 
	 * @return <code>limit</code
	 */
	public Integer getLimit() {
		return limit;
	}

	/**
	 * It sets the <code>limit</code>
	 * 
	 * @param limit
	 *            The limit of characteres per line
	 */
	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	/**
	 * It returns the <code>justify</code> flag
	 * 
	 * @return <code>justify</code>
	 */
	public boolean isJustify() {
		return justify;
	}

	/**
	 * It sets the <code>justify<code> flag
	 * 
	 * @param justify
	 *            Tells if it should identify the text or not
	 */
	public void setJustify(boolean justify) {
		this.justify = justify;
	}
}
