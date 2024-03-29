package ddwucom.mobile.report02.report02_01_20160989;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

public class BakeryXmlParser {
    public enum TagType { NONE, TITLE, ADDRESS, TEL };

    public BakeryXmlParser() {
    }

    public ArrayList<BakeryDto> parse(String xml) {

        ArrayList<BakeryDto> resultList = new ArrayList();
        BakeryDto dto = null;

        TagType tagType = TagType.NONE;

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xml));

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.END_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals("item")) {
                            dto = new BakeryDto();
                        } else if (parser.getName().equals("title")) {
                            if (dto != null) tagType = TagType.TITLE;
                        } else if (parser.getName().equals("address")) {
                            if (dto != null) tagType = TagType.ADDRESS;
                        } else if (parser.getName().equals("telephone")) {
                            if (dto != null) tagType = TagType.TEL;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("item")) {
                            resultList.add(dto);
                            dto = null;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        switch(tagType) {
                            case TITLE:
                                //타이틀에 <b>나 </b>가 있을 경우 replaceAll 메소드를 사용하여 없애주는 작업
                                String text = parser.getText().replaceAll("<b>", "").replaceAll("</b>", "");
                                dto.setTitle(text);
                                break;
                            case ADDRESS:
                                dto.setAddress(parser.getText());
                                break;
                            case TEL:
                                dto.setTelephone(parser.getText());
                                break;
                        }
                        tagType = TagType.NONE;
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultList;
    }
}
