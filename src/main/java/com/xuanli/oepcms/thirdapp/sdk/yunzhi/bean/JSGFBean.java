package com.xuanli.oepcms.thirdapp.sdk.yunzhi.bean;

public class JSGFBean {
	private String Version = "1";
	private String DisplayText = "Jsgf Grammar Tool Generated";
	private String GrammarWeight;
	private String Grammar;

	public String getVersion() {
		return Version;
	}

	public void setVersion(String version) {
		Version = version;
	}

	public String getDisplayText() {
		return DisplayText;
	}

	public void setDisplayText(String displayText) {
		DisplayText = displayText;
	}

	public String getGrammarWeight() {
		return GrammarWeight;
	}

	public void setGrammarWeight(String grammarWeight) {
		GrammarWeight = grammarWeight;
	}

	public String getGrammar() {
		return Grammar;
	}

	public void setGrammar(String grammar) {
		Grammar = grammar;
	}

	public JSGFBean(String grammarWeight, String grammar) {
		super();
		GrammarWeight = grammarWeight;
		Grammar = "#JSGF V1.0 utf-8 cn;\ngrammar main;\n public <main> = \"<s>\""+grammar+"\"</s>\";\n";
	}

}
