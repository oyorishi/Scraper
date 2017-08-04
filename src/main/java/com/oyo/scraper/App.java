package com.oyo.scraper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * http://transportindia.co.in/directory/datalist.asp?start=
 */
public class App {

	public static void main(String[] args) throws IOException {
		HashMap<String, TransportIndia> transportMap = new HashMap<>();
		String sampleUrl = "sample url";
		int ct = 0;
		for (int i = 0; i < 25; i++) {
			int count = 10 * i + 1;
			String url = sampleUrl + count;
			Document doc = Jsoup.connect(url).get();
			Element table = doc.select("table").get(2);
			for (Element row : table.select("tr")) {
				Elements tds = row.select("td");
				for (Element td : tds) {
					Elements fonts = td.select("font");
					if (fonts.size() == 13) {
						TransportIndia transportIndia = new TransportIndia();
						String name = fonts.get(1).text();
						String address = fonts.get(2).text();
						String phone = fonts.get(3).text();
						transportIndia.setName(name).setAddress(address).setPhone(phone);
						ct++;
						System.out.println("count:" + ct + ":putting to map:" + name);
						if (transportMap.containsKey(name)) {
							name = name + "duplicate hu mai";
						}
						transportMap.put(name, transportIndia);
					}
				}
			}
		}
		System.out.println("total:" + transportMap.size());
		for (Entry<String, TransportIndia> entry : transportMap.entrySet()) {
			TransportIndia transportIndia = entry.getValue();
			System.out.println("name:" + entry.getKey());
			System.out.println("address:" + transportIndia.getAddress());
			System.out.println("phone:" + transportIndia.getPhone());
			System.out.println();
			System.out.println();
		}

	}
}
