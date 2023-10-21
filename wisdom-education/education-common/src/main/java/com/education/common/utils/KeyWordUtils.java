package com.education.common.utils;

/*
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;
*/

/**
 *  详细参考 https://www.jb51.net/article/74418.htm
 * 获取文章关键词工具类
 * @author zengjintao
 * @version 1.0
 * @create 2018/12/9 15:35
 **/
public class KeyWordUtils {

    private static final int DEFAULT_KEYWORD_NUMBER = 1000;
    private static final int DEFAULT_GROUP_COUNT = 1;

   /* private static List<String> extract(String content, int groupCount) throws IOException {
        List<String> list = new ArrayList<String>(); //定义一个list来接收将要截取出来单词
        IKAnalyzer analyzer = new IKAnalyzer(); //初始化IKAnalyzer
        analyzer.setUseSmart(true); //将IKAnalyzer设置成智能截取
        TokenStream tokenStream = analyzer.tokenStream("", new StringReader(content));//调用tokenStream方法(读取文章的字符流)
        while (tokenStream.incrementToken()) { //循环获得截取出来的单词
            CharTermAttribute charTermAttribute = tokenStream.getAttribute(CharTermAttribute.class);//转换为char类型
            String keWord= charTermAttribute.toString(); //转换为String类型
            if (keWord.length() > groupCount) { //判断截取关键字在几个单词以上的数量(默认为2个单词以上)
                list.add(keWord); //将最终获得的单词放入list集合中
            }
        }
        return list;
    }
    *//**
     * 将list中的集合转换成Map中的key，value为数量默认为1
     * @param list
     * @return
     *//*
    private static Map<String, Integer> listToMap(List<String> list){
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (String key : list) { //循环获得的List集合
            if (list.contains(key)) { //判断这个集合中是否存在该字符串
                map.put(key, map.get(key) == null ? 1 : map.get(key)+1);
            } //将集中获得的字符串放在map的key键上
        } //并计算其value是否有值，如有则+1操作
        return map;
    }

    public static List<String> getKeyWords(String content) throws IOException {
        return getKeyWords(content, DEFAULT_GROUP_COUNT, DEFAULT_KEYWORD_NUMBER);
    }

    *//**
     * 获取关键词
     * @param content 内容
     * @param groupCount 截取关键字在几个单词以上的数量
     * @param keyWordNumber  获取关键字个数
     * @return
     *//*
    public static List<String> getKeyWords(String content, int groupCount, int keyWordNumber) throws IOException{
        List<String> keyWordsList= extract(content, groupCount); //调用提取单词方法
        Map<String, Integer> map = listToMap(keyWordsList); //list转map并计次数
        //使用Collections的比较方法进行对map中value的排序
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String,Integer>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> source, Map.Entry<String, Integer> target) {
                return (target.getValue() - source.getValue());
            }
        });
        if (list.size() < keyWordNumber) {
            keyWordNumber = list.size(); //排序后的长度，以免获得到null的字符
        }
        List<String> keyWords = new ArrayList<>(keyWordNumber);
        for(int i = 0; i < list.size(); i++) { //循环排序后的数组
            if (i < keyWordNumber) { //判断个数
                keyWords.add(list.get(i).getKey());//添加关键字到集合
            }
        }
        return keyWords;
    }

    public static void main(String[] args) throws IOException {
        String title = "北京时间1月4日篮网在主场以122-123一分惜败华盛顿奇才队。先来看下两队数据：\n" +
                "\n" +
                "奇才数据：拉塞尔-威斯布鲁克24分5篮板10助攻2抢断；布拉德利-比尔27分10篮板5助攻2封盖；托马斯-布莱恩特21分14篮板2助攻；戴维斯-贝尔坦斯15分6篮板；八村垒15分2篮板3助攻。\n" +
                "\n" +
                "威少狂打18铁，杜少绝杀失手，奇才123-122险胜篮网\n" +
                "篮网数据：凯文-杜兰特28分11篮板7助攻；凯里-欧文30分5篮板10助攻2抢断2封盖；乔-哈里斯16分2篮板2抢断；蒂莫泰-卢瓦乌-卡巴罗14分4篮板1助攻；贾勒特-阿伦14分11篮板4封盖。\n" +
                "\n" +
                "此前刚刚取得赛季首胜的奇才队本场继续保持着不错的状态，首节比赛开始篮网优先获得球权，欧文开局手风极顺率先得手，篮网后场组合也帮助球队稳住比分，两队内外开花比分相持不下，洛佩斯的罚球帮助球队以29-28结束首节。\n" +
                "\n" +
                "威少狂打18铁，杜少绝杀失手，奇才123-122险胜篮网\n" +
                "次节比赛，奇才球队状态火热，他们连续依靠中投将比分逐步扩大到两位数，好在此后篮网逐渐找回状态,欧文和杜兰特分别在外线命中三分将分差逐步迫近，而卢瓦乌-卡巴罗的三分则帮助球队追平比分。第二节末段，奇才队继续打出一波小高潮重新夺回领先优势,不过欧文和杜兰特也不甘示弱，在他俩的带领下篮网在最后将比分追至71-70反超1分。\n" +
                "\n" +
                "威少狂打18铁，杜少绝杀失手，奇才123-122险胜篮网\n" +
                "下半场开始，双方继续进行拉锯战，布莱恩特的补篮并加罚让奇才扳平反超比分，双方分差一直不超过五分，这样的状况持续了一整节，欧文的两记罚球让球队以93-94进入末节。\n" +
                "\n" +
                "威少狂打18铁，杜少绝杀失手，奇才123-122险胜篮网\n" +
                "末节开始，阿伦的扣篮让篮网反超比分，但这个领先并没有保持多久便被打破，哈里斯的三分让球队再次夺回领先，此后比尔在内线助攻贝尔塔斯投进三分，之后篮下又自己打进两分，奇才队瞬间打出一波7-0的进攻重新夺回领先。\n" +
                "\n" +
                "威少狂打18铁，杜少绝杀失手，奇才123-122险胜篮网\n" +
                "比赛最后时刻，杜兰特的上篮帮助篮网反超一分。暂停回来之后，比尔助攻布莱恩特的扣篮让奇才手握最后的领先，此时时间只剩下14秒，暂停之后，欧文在外线三分不中，杜兰特抢下篮板空位中投偏出，最终奇才以123-122险胜奇才赢得两连胜。";
        System.out.println(getKeyWords(title.replaceAll("\n", ""), 1, 50));
    }*/

    /**
     * 对字符串进行分词
     * @param content
     * @return
     */
    /*public static List<String> groupByContent(String content) {
        JiebaSegmenter segmenter = new JiebaSegmenter();
        String sentences = "这是一个伸手不见五指的黑夜。我叫孙悟空，我爱北京，我爱Python和C++。\", \"我不喜欢日本和服";
        System.out.println(segmenter.process(sentences, JiebaSegmenter.SegMode.INDEX).toString());
        return null;
    }*/
}
