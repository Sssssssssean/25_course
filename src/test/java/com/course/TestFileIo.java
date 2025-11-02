package com.course;

import com.course.pojo.PointObject;
import com.course.utils.FileUtils;
import com.course.utils.JsonUtils;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author lixuy
 * Created on 2019-04-10
 */
public class TestFileIo {

    @Test
    public void testWrite(){
        try {
            PointObject pointObject = new PointObject();
            pointObject.setId(1);
            pointObject.setGrowScore(10);
            pointObject.setExchangeScore(5);
            pointObject.setScoreTotal(15);
            pointObject.setProfileInputed(true);
            pointObject.setBloodSugarCount(5);
            pointObject.setLastComplicationTime("2024-01-01 10:00:00");
            pointObject.setLastYdqnTime("2024-02-01 10:00:00");

            String json = JsonUtils.objectToJson(pointObject);
            assertNotNull("JSON字符串不能为空", json);
            assertTrue("JSON字符串应该包含id", json.contains("\"id\":1"));
            assertTrue("JSON字符串应该包含growScore", json.contains("\"growScore\":10"));
            assertTrue("JSON字符串应该包含exchangeScore", json.contains("\"exchangeScore\":5"));

            FileUtils.writeFile("score",json);

            // 验证文件写入成功
            String readContent = FileUtils.readFile("score");
            assertEquals("写入和读取的内容应该一致", json, readContent);

            System.out.println("文件写入测试通过");
        }catch (Exception e){
            e.printStackTrace();
            Assert.fail("文件写入测试失败: " + e.getMessage());
        }
    }

    @Test
    public void testRead(){
        try {
            // 先写入测试数据
            PointObject originalObject = new PointObject();
            originalObject.setId(2);
            originalObject.setGrowScore(20);
            originalObject.setExchangeScore(15);
            originalObject.setScoreTotal(35);
            originalObject.setProfileInputed(false);
            originalObject.setBloodSugarCount(8);
            originalObject.setLastComplicationTime("2023-12-01 15:30:00");
            originalObject.setLastYdqnTime("2024-01-15 09:45:00");

            String json = JsonUtils.objectToJson(originalObject);
            FileUtils.writeFile("score_test", json);

            // 读取并验证
            String file = FileUtils.readFile("score_test");
            assertNotNull("读取的文件内容不能为空", file);
            System.out.println("读取的JSON: " + file);

            PointObject pointObject = JsonUtils.jsonToPojo(file, PointObject.class);
            assertNotNull("反序列化的对象不能为空", pointObject);

            // 验证各个字段
            assertEquals("ID应该一致", (long)originalObject.getId().intValue(), (long)pointObject.getId().intValue());
            assertEquals("成长积分应该一致", (long)originalObject.getGrowScore().intValue(), (long)pointObject.getGrowScore().intValue());
            assertEquals("可兑换积分应该一致", (long)originalObject.getExchangeScore().intValue(), (long)pointObject.getExchangeScore().intValue());
            assertEquals("总积分应该一致", (long)originalObject.getScoreTotal().intValue(), (long)pointObject.getScoreTotal().intValue());
            assertEquals("个人信息填写状态应该一致", originalObject.getProfileInputed(), pointObject.getProfileInputed());
            assertEquals("血糖记录次数应该一致", (long)originalObject.getBloodSugarCount().intValue(), (long)pointObject.getBloodSugarCount().intValue());
            assertEquals("并发症时间应该一致", originalObject.getLastComplicationTime(), pointObject.getLastComplicationTime());
            assertEquals("胰岛功能检测时间应该一致", originalObject.getLastYdqnTime(), pointObject.getLastYdqnTime());

            System.out.println("反序列化对象: " + pointObject);
            System.out.println("文件读取测试通过");
        }catch (Exception e){
            e.printStackTrace();
            Assert.fail("文件读取测试失败: " + e.getMessage());
        }
    }

    @Test
    public void testJsonSerialization() {
        try {
            // 测试JSON序列化和反序列化的完整性
            PointObject original = new PointObject();
            original.setId(999);
            original.setGrowScore(100);
            original.setExchangeScore(50);
            original.setScoreTotal(150);
            original.setProfileInputed(true);
            original.setBloodSugarCount(25);
            original.setLastComplicationTime("2024-06-17 16:00:00");
            original.setLastYdqnTime("2024-06-17 16:30:00");

            // 序列化
            String json = JsonUtils.objectToJson(original);
            assertNotNull("序列化结果不能为空", json);
            assertTrue("JSON应该包含所有字段", json.length() > 50);

            // 反序列化
            PointObject deserialized = JsonUtils.jsonToPojo(json, PointObject.class);
            assertNotNull("反序列化结果不能为空", deserialized);

            // 验证所有字段
            assertEquals("ID序列化测试", (long)original.getId().intValue(), (long)deserialized.getId().intValue());
            assertEquals("成长积分序列化测试", (long)original.getGrowScore().intValue(), (long)deserialized.getGrowScore().intValue());
            assertEquals("可兑换积分序列化测试", (long)original.getExchangeScore().intValue(), (long)deserialized.getExchangeScore().intValue());
            assertEquals("总积分序列化测试", (long)original.getScoreTotal().intValue(), (long)deserialized.getScoreTotal().intValue());
            assertEquals("个人信息状态序列化测试", original.getProfileInputed(), deserialized.getProfileInputed());
            assertEquals("血糖次数序列化测试", (long)original.getBloodSugarCount().intValue(), (long)deserialized.getBloodSugarCount().intValue());
            assertEquals("并发症时间序列化测试", original.getLastComplicationTime(), deserialized.getLastComplicationTime());
            assertEquals("胰岛功能时间序列化测试", original.getLastYdqnTime(), deserialized.getLastYdqnTime());

            System.out.println("JSON序列化测试通过");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("JSON序列化测试失败: " + e.getMessage());
        }
    }

}
