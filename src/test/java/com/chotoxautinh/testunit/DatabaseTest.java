/**
 * @author chotoxautinh
 *
 * Apr 24, 2016 - http://chotoxautinh.com/
 */
package com.chotoxautinh.testunit;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.chotoxautinh.game.config.Constant;
import com.chotoxautinh.game.model.HighScore;
import com.chotoxautinh.util.FileUtils;
import com.chotoxautinh.util.JsonUtils;
import com.chotoxautinh.util.ListUtils;

public class DatabaseTest {
	public static void main(String[] args) {
		URL dbURL = Constant.DATABASE;

		String content = FileUtils.readFile(dbURL);
		System.out.println(content);

		HighScore[] arr = JsonUtils.fromJson(content, HighScore[].class);
		List<HighScore> list = new ArrayList<>(Arrays.asList(arr));
		System.out.println(list.size());

		ListUtils.<HighScore> insert(list, new HighScore("Cho To Xau Tinh", 121036));
		FileUtils.writeFile(dbURL, JsonUtils.toJson(list));

		System.out.println(FileUtils.readFile(dbURL));
	}
}
