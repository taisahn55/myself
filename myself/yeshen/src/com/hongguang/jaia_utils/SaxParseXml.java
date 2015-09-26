package com.hongguang.jaia_utils;

import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxParseXml extends DefaultHandler {

	private String preTag = null;// 作用是记录解析时的上一个节点名称
	private ArrayList<String> datas;
	public ArrayList<String> getDatas(InputStream xmlStream) throws Exception {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		parser.parse(xmlStream, this);
		return datas;
	}

	public ArrayList<String> getDatas(String xmlDate) throws Exception {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		InputSource is = new InputSource(new StringReader(xmlDate));
		parser.parse(is, this);
		return datas;
	}

	@Override
	public void startDocument() throws SAXException {
		datas = new ArrayList<String>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		preTag = qName;// 将正在解析的节点名称赋给preTag
		// System.out.println("解析到节点开头");
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		preTag = null;
		/**
		 * 当解析结束时置为空。这里很重要，例如，当图中画3的位置结束后，会调用这个方法
		 * ，如果这里不把preTag置为null，根据startElement(....)方法，preTag的值还是book，当文档顺序读到图
		 * 中标记4的位置时，会执行characters(char[] ch, int start, int
		 * length)这个方法，而characters(....)方
		 * 法判断preTag!=null，会执行if判断的代码，这样就会把空值赋值给book，这不是我们想要的。
		 */
		// System.out.println("解析到节点末尾");
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (preTag != null) {
			String content = new String(ch, start, length);
			if ("version".equals(preTag)) {
				// System.out.println("解析到version");
				datas.add(content);
			} else if ("pack".equals(preTag)) {
				datas.add(content);
			} else if ("url".equals(preTag)) {
				// System.out.println("解析到url");
				datas.add(content);
			} else if ("force".equals(preTag)) {
				// System.out.println("解析到force");
				datas.add(content);
			}
		}
	}

}
