package domain.service;

/**
 * 何らかの物から要素を引き抜くインターフェイス。
 */
public interface Extractor {
	/**
	 * DocumentやElementsから目的の要素を引き抜く
	 */
	public void extract();
}
