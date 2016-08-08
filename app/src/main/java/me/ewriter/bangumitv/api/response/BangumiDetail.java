package me.ewriter.bangumitv.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Zubin on 2016/8/3.
 * 番剧详情 staff 信息的 info.alias 一部分返回 JsonArray，一部分返回JsonObject，移除掉了alias 信息
 * crt.info.alias 也有这个问题，真是跪了，不做处理了，crt这个出现的比较少
 */
public class BangumiDetail implements Serializable{
    private static final long serialVersionUID = 507697275449133999L;
    /**
     * id : 160124
     * url : http://bgm.tv/subject/160124
     * type : 2
     * name : 食戟のソーマ 弍ノ皿
     * name_cn : 食戟之灵 贰之皿
     * summary : 每天都在自家的市郊餐馆“幸平”帮忙的幸平创真，在初中毕业的同时，就被父亲·城一郎推荐编入某所料理学校。
     那是日本首屈一指的料理学校“远月茶寮料理学园”……是毕业到达率不足10%的超级精英学校。
     严酷的考试、不断有人脱落的地狱宿泊研修、以退学为赌注的食戟。
     跨越各种试炼，磨炼着料理水平的创真。
     他与志同道合的伙伴，以及同世代的实力者们相遇，作为一个人类也有着确实的成长。
     ——之后，秋季到来。
     即使在一年生当中也只允许被选拔出来的人出场的远月传统“秋之选拔”，创真得到了这场选拔的出场券。
     “秋之选拔”预选的题目是“咖喱料理”。
     优胜候补的调味料名手，叶山亮所制作的咖喱当中调味料的那份奥妙，得知这一点的创真为了应对叶山的宣战布告，而与调味料展开格斗。
     他直到预选当天的早晨为止都在试行错误当中面临调理，虽然抵达了与叶山相同的“香味炸弹”这一想法，但结果以叶山94分、创真93分的微小差距，令叶山夺得第一名。
     虽然败在叶山手下，创真仍然决定漂亮地在正赛中出场。
     他在满溢不甘的同时，在心中发誓要变得更强，如此这般，“秋之选拔”的正赛到来了。
     在淘汰赛中并肩出场的是，创真、惠、塔克米、绯沙子、爱丽丝、黑木场、叶山、美作这8人。
     创真的下一个对手是……！？
     以及，抵达顶点之人将会是……！？
     * eps : [{"id":640770,"url":"http://bgm.tv/ep/640770","type":0,"sort":1,"name":"その箱に詰めるもの","name_cn":"盒中所装之物","duration":"00:24:38","airdate":"2016-07-02","comment":60,"desc":"","status":"Air"},{"id":640771,"url":"http://bgm.tv/ep/640771","type":0,"sort":2,"name":"交錯する光と影","name_cn":"交错的光与影","duration":"00:24:30","airdate":"2016-07-09","comment":41,"desc":"","status":"Air"},{"id":640772,"url":"http://bgm.tv/ep/640772","type":0,"sort":3,"name":"『玉』の世代","name_cn":"『璞玉』世代","duration":"00:24:30","airdate":"2016-07-16","comment":41,"desc":"","status":"Air"},{"id":640773,"url":"http://bgm.tv/ep/640773","type":0,"sort":4,"name":"追跡者","name_cn":"追踪者","duration":"00:24:30","airdate":"2016-07-23","comment":54,"desc":"","status":"Air"},{"id":640774,"url":"http://bgm.tv/ep/640774","type":0,"sort":5,"name":"一口目の秘密","name_cn":"第一口的秘密","duration":"00:24:30","airdate":"2016-07-30","comment":22,"desc":"","status":"Air"},{"id":640775,"url":"http://bgm.tv/ep/640775","type":0,"sort":6,"name":"朝はまた来る","name_cn":"清晨依旧来临","duration":"00:24:30","airdate":"2016-08-06","comment":0,"desc":"","status":"NA"},{"id":640776,"url":"http://bgm.tv/ep/640776","type":0,"sort":7,"name":"","name_cn":"","duration":"00:24:30","airdate":"2016-08-13","comment":0,"desc":"","status":"NA"},{"id":640777,"url":"http://bgm.tv/ep/640777","type":0,"sort":8,"name":"","name_cn":"","duration":"00:24:30","airdate":"2016-08-20","comment":0,"desc":"","status":"NA"},{"id":640778,"url":"http://bgm.tv/ep/640778","type":0,"sort":9,"name":"","name_cn":"","duration":"00:24:30","airdate":"2016-08-27","comment":0,"desc":"","status":"NA"},{"id":640779,"url":"http://bgm.tv/ep/640779","type":0,"sort":10,"name":"","name_cn":"","duration":"00:24:30","airdate":"2016-09-03","comment":0,"desc":"","status":"NA"},{"id":640780,"url":"http://bgm.tv/ep/640780","type":0,"sort":11,"name":"","name_cn":"","duration":"00:24:30","airdate":"2016-09-10","comment":0,"desc":"","status":"NA"},{"id":640781,"url":"http://bgm.tv/ep/640781","type":0,"sort":12,"name":"","name_cn":"","duration":"00:24:30","airdate":"2016-09-17","comment":0,"desc":"","status":"NA"},{"id":644739,"url":"http://bgm.tv/ep/644739","type":0,"sort":13,"name":"","name_cn":"","duration":"00:24:30","airdate":"2016-09-24","comment":0,"desc":"","status":"NA"}]
     * air_date : 2016-07-02
     * air_weekday : 6
     * rating : {"total":302,"count":{"1":2,"2":0,"3":0,"4":0,"5":10,"6":27,"7":120,"8":120,"9":16,"10":7},"score":7.4}
     * rank : 988
     * images : {"large":"http://lain.bgm.tv/pic/cover/l/57/35/160124_Yhhq2.jpg","common":"http://lain.bgm.tv/pic/cover/c/57/35/160124_Yhhq2.jpg","medium":"http://lain.bgm.tv/pic/cover/m/57/35/160124_Yhhq2.jpg","small":"http://lain.bgm.tv/pic/cover/s/57/35/160124_Yhhq2.jpg","grid":"http://lain.bgm.tv/pic/cover/g/57/35/160124_Yhhq2.jpg"}
     * collection : {"wish":102,"collect":26,"doing":1111,"on_hold":17,"dropped":9}
     * crt : [{"id":19453,"url":"http://bgm.tv/character/19453","name":"薙切 えりな","name_cn":"薙切绘里奈","role_name":"主角","images":{"large":"http://lain.bgm.tv/pic/crt/l/b6/40/19453_crt_Mfh8Y.jpg","medium":"http://lain.bgm.tv/pic/crt/m/b6/40/19453_crt_Mfh8Y.jpg","small":"http://lain.bgm.tv/pic/crt/s/b6/40/19453_crt_Mfh8Y.jpg","grid":"http://lain.bgm.tv/pic/crt/g/b6/40/19453_crt_Mfh8Y.jpg"},"comment":11,"collects":43,"info":{"name_cn":"薙切绘里奈","alias":{"jp":"薙切えりな","kana":"なきり えりな","romaji":"Nakiri erina"},"gender":"女"},"actors":[{"id":8138,"url":"http://bgm.tv/person/8138","name":"種田梨沙","images":{"large":"http://lain.bgm.tv/pic/crt/l/07/06/8138_prsn_PBCx5.jpg?r=1464881523","medium":"http://lain.bgm.tv/pic/crt/m/07/06/8138_prsn_PBCx5.jpg?r=1464881523","small":"http://lain.bgm.tv/pic/crt/s/07/06/8138_prsn_PBCx5.jpg?r=1464881523","grid":"http://lain.bgm.tv/pic/crt/g/07/06/8138_prsn_PBCx5.jpg?r=1464881523"}}]},{"id":19452,"url":"http://bgm.tv/character/19452","name":"幸平 創真","name_cn":"幸平创真","role_name":"主角","images":{"large":"http://lain.bgm.tv/pic/crt/l/ff/80/19452_crt_Nza5q.jpg","medium":"http://lain.bgm.tv/pic/crt/m/ff/80/19452_crt_Nza5q.jpg","small":"http://lain.bgm.tv/pic/crt/s/ff/80/19452_crt_Nza5q.jpg","grid":"http://lain.bgm.tv/pic/crt/g/ff/80/19452_crt_Nza5q.jpg"},"comment":25,"collects":23,"info":{"name_cn":"幸平创真","alias":{"jp":"幸平創真","kana":"ゆきひら そうま","romaji":"Yukihira souma","nick":"药王"},"gender":"男","bloodtype":"B","height":"171cm","weight":"57kg"},"actors":[{"id":5764,"url":"http://bgm.tv/person/5764","name":"松岡禎丞","images":{"large":"http://lain.bgm.tv/pic/crt/l/91/77/5764_prsn_njsm3.jpg?r=1447773082","medium":"http://lain.bgm.tv/pic/crt/m/91/77/5764_prsn_njsm3.jpg?r=1447773082","small":"http://lain.bgm.tv/pic/crt/s/91/77/5764_prsn_njsm3.jpg?r=1447773082","grid":"http://lain.bgm.tv/pic/crt/g/91/77/5764_prsn_njsm3.jpg?r=1447773082"}}]},{"id":19454,"url":"http://bgm.tv/character/19454","name":"田所 恵","name_cn":"田所惠","role_name":"主角","images":{"large":"http://lain.bgm.tv/pic/crt/l/a3/5d/19454_crt_8WAeJ.jpg","medium":"http://lain.bgm.tv/pic/crt/m/a3/5d/19454_crt_8WAeJ.jpg","small":"http://lain.bgm.tv/pic/crt/s/a3/5d/19454_crt_8WAeJ.jpg","grid":"http://lain.bgm.tv/pic/crt/g/a3/5d/19454_crt_8WAeJ.jpg"},"comment":27,"collects":57,"info":{"name_cn":"田所惠","alias":{"jp":"田所恵","nick":"極星の清涼剤"},"gender":"女"},"actors":[{"id":10757,"url":"http://bgm.tv/person/10757","name":"高橋未奈美","images":{"large":"http://lain.bgm.tv/pic/crt/l/e5/5f/10757_prsn_x9X9y.jpg?r=1455253086","medium":"http://lain.bgm.tv/pic/crt/m/e5/5f/10757_prsn_x9X9y.jpg?r=1455253086","small":"http://lain.bgm.tv/pic/crt/s/e5/5f/10757_prsn_x9X9y.jpg?r=1455253086","grid":"http://lain.bgm.tv/pic/crt/g/e5/5f/10757_prsn_x9X9y.jpg?r=1455253086"}}]},{"id":43034,"url":"http://bgm.tv/character/43034","name":"佐藤昭二","name_cn":"佐藤昭二","role_name":"配角","images":{"large":"http://lain.bgm.tv/pic/crt/l/b6/30/43034_crt_F189C.jpg?r=1467552759","medium":"http://lain.bgm.tv/pic/crt/m/b6/30/43034_crt_F189C.jpg?r=1467552759","small":"http://lain.bgm.tv/pic/crt/s/b6/30/43034_crt_F189C.jpg?r=1467552759","grid":"http://lain.bgm.tv/pic/crt/g/b6/30/43034_crt_F189C.jpg?r=1467552759"},"comment":0,"collects":1,"info":{"name_cn":"佐藤昭二","alias":{"kana":"さとうしょうじ"},"gender":"男"},"actors":[{"id":7548,"url":"http://bgm.tv/person/7548","name":"河西健吾","images":{"large":"http://lain.bgm.tv/pic/crt/l/83/01/7548_prsn_0Zmbx.jpg","medium":"http://lain.bgm.tv/pic/crt/m/83/01/7548_prsn_0Zmbx.jpg","small":"http://lain.bgm.tv/pic/crt/s/83/01/7548_prsn_0Zmbx.jpg","grid":"http://lain.bgm.tv/pic/crt/g/83/01/7548_prsn_0Zmbx.jpg"}}]},{"id":43033,"url":"http://bgm.tv/character/43033","name":"青木大吾","name_cn":"青木大吾","role_name":"配角","images":{"large":"http://lain.bgm.tv/pic/crt/l/9f/fa/43033_crt_mbXn9.jpg?r=1467552102","medium":"http://lain.bgm.tv/pic/crt/m/9f/fa/43033_crt_mbXn9.jpg?r=1467552102","small":"http://lain.bgm.tv/pic/crt/s/9f/fa/43033_crt_mbXn9.jpg?r=1467552102","grid":"http://lain.bgm.tv/pic/crt/g/9f/fa/43033_crt_mbXn9.jpg?r=1467552102"},"comment":0,"collects":1,"info":{"name_cn":"青木大吾","alias":{"kana":"あおきだいご"},"gender":"男"},"actors":[{"id":6278,"url":"http://bgm.tv/person/6278","name":"柳田淳一","images":{"large":"http://lain.bgm.tv/pic/crt/l/dc/37/6278_prsn_sDPAG.jpg","medium":"http://lain.bgm.tv/pic/crt/m/dc/37/6278_prsn_sDPAG.jpg","small":"http://lain.bgm.tv/pic/crt/s/dc/37/6278_prsn_sDPAG.jpg","grid":"http://lain.bgm.tv/pic/crt/g/dc/37/6278_prsn_sDPAG.jpg"}}]},{"id":42311,"url":"http://bgm.tv/character/42311","name":"叡山枝津也","name_cn":"","role_name":"配角","images":{"large":"http://lain.bgm.tv/pic/crt/l/f4/2c/42311_crt_Bys0Z.jpg?r=1465212281","medium":"http://lain.bgm.tv/pic/crt/m/f4/2c/42311_crt_Bys0Z.jpg?r=1465212281","small":"http://lain.bgm.tv/pic/crt/s/f4/2c/42311_crt_Bys0Z.jpg?r=1465212281","grid":"http://lain.bgm.tv/pic/crt/g/f4/2c/42311_crt_Bys0Z.jpg?r=1465212281"},"comment":0,"collects":1,"info":{"alias":{"kana":"えいざん えつや"},"gender":"男"},"actors":[{"id":4513,"url":"http://bgm.tv/person/4513","name":"杉田智和","images":{"large":"http://lain.bgm.tv/pic/crt/l/72/98/4513_prsn_F3TZf.jpg?r=1424955460","medium":"http://lain.bgm.tv/pic/crt/m/72/98/4513_prsn_F3TZf.jpg?r=1424955460","small":"http://lain.bgm.tv/pic/crt/s/72/98/4513_prsn_F3TZf.jpg?r=1424955460","grid":"http://lain.bgm.tv/pic/crt/g/72/98/4513_prsn_F3TZf.jpg?r=1424955460"}}]},{"id":42118,"url":"http://bgm.tv/character/42118","name":"美作 昴","name_cn":"美作昴","role_name":"配角","images":{"large":"http://lain.bgm.tv/pic/crt/l/71/9a/42118_crt_G5Xe5.jpg?r=1467549058","medium":"http://lain.bgm.tv/pic/crt/m/71/9a/42118_crt_G5Xe5.jpg?r=1467549058","small":"http://lain.bgm.tv/pic/crt/s/71/9a/42118_crt_G5Xe5.jpg?r=1467549058","grid":"http://lain.bgm.tv/pic/crt/g/71/9a/42118_crt_G5Xe5.jpg?r=1467549058"},"comment":3,"collects":2,"info":{"name_cn":"美作昴","alias":{"kana":"みまさか すばる"},"gender":"男"},"actors":[{"id":4483,"url":"http://bgm.tv/person/4483","name":"安元洋貴","images":{"large":"http://lain.bgm.tv/pic/crt/l/e3/8f/4483_prsn_gSAy9.jpg?r=1427030809","medium":"http://lain.bgm.tv/pic/crt/m/e3/8f/4483_prsn_gSAy9.jpg?r=1427030809","small":"http://lain.bgm.tv/pic/crt/s/e3/8f/4483_prsn_gSAy9.jpg?r=1427030809","grid":"http://lain.bgm.tv/pic/crt/g/e3/8f/4483_prsn_gSAy9.jpg?r=1427030809"}}]},{"id":19948,"url":"http://bgm.tv/character/19948","name":"榊 涼子","name_cn":"榊凉子","role_name":"配角","images":{"large":"http://lain.bgm.tv/pic/crt/l/d4/08/19948_crt_9RlE3.jpg","medium":"http://lain.bgm.tv/pic/crt/m/d4/08/19948_crt_9RlE3.jpg","small":"http://lain.bgm.tv/pic/crt/s/d4/08/19948_crt_9RlE3.jpg","grid":"http://lain.bgm.tv/pic/crt/g/d4/08/19948_crt_9RlE3.jpg"},"comment":2,"collects":12,"info":{"name_cn":"榊凉子","alias":{"jp":"榊涼子","kana":"さかき りょうこ","romaji":"Sakaki ryouko"},"gender":"女"},"actors":[{"id":5847,"url":"http://bgm.tv/person/5847","name":"茅野愛衣","images":{"large":"http://lain.bgm.tv/pic/crt/l/8d/62/5847_prsn_ez2M5.jpg","medium":"http://lain.bgm.tv/pic/crt/m/8d/62/5847_prsn_ez2M5.jpg","small":"http://lain.bgm.tv/pic/crt/s/8d/62/5847_prsn_ez2M5.jpg","grid":"http://lain.bgm.tv/pic/crt/g/8d/62/5847_prsn_ez2M5.jpg"}}]},{"id":19947,"url":"http://bgm.tv/character/19947","name":"吉野 悠姫","name_cn":"吉野悠姬","role_name":"配角","images":{"large":"http://lain.bgm.tv/pic/crt/l/5b/87/19947_crt_M3elq.jpg","medium":"http://lain.bgm.tv/pic/crt/m/5b/87/19947_crt_M3elq.jpg","small":"http://lain.bgm.tv/pic/crt/s/5b/87/19947_crt_M3elq.jpg","grid":"http://lain.bgm.tv/pic/crt/g/5b/87/19947_crt_M3elq.jpg"},"comment":3,"collects":13,"info":{"name_cn":"吉野悠姬","alias":{"jp":"吉野悠姫","kana":"よしの ゆうき","romaji":"Yoshino yuuki"},"gender":"女"},"actors":[{"id":6724,"url":"http://bgm.tv/person/6724","name":"内田真礼","images":{"large":"http://lain.bgm.tv/pic/crt/l/4c/23/6724_prsn_bwX7f.jpg","medium":"http://lain.bgm.tv/pic/crt/m/4c/23/6724_prsn_bwX7f.jpg","small":"http://lain.bgm.tv/pic/crt/s/4c/23/6724_prsn_bwX7f.jpg","grid":"http://lain.bgm.tv/pic/crt/g/4c/23/6724_prsn_bwX7f.jpg"}}]}]
     * staff : [{"id":9284,"url":"http://bgm.tv/person/9284","name":"附田祐斗","name_cn":"","role_name":"","images":{"large":"http://lain.bgm.tv/pic/crt/l/61/2d/9284_prsn_x0XQq.jpg","medium":"http://lain.bgm.tv/pic/crt/m/61/2d/9284_prsn_x0XQq.jpg","small":"http://lain.bgm.tv/pic/crt/s/61/2d/9284_prsn_x0XQq.jpg","grid":"http://lain.bgm.tv/pic/crt/g/61/2d/9284_prsn_x0XQq.jpg"},"comment":6,"collects":0,"info":{"alias":{"kana":"つくだ ゆうと"},"Twitter":"@tsukudayuto"},"jobs":["原作"]},{"id":7977,"url":"http://bgm.tv/person/7977","name":"佐伯俊","name_cn":"佐伯俊","role_name":"","images":{"large":"http://lain.bgm.tv/pic/crt/l/23/02/7977_prsn_eBB6P.jpg","medium":"http://lain.bgm.tv/pic/crt/m/23/02/7977_prsn_eBB6P.jpg","small":"http://lain.bgm.tv/pic/crt/s/23/02/7977_prsn_eBB6P.jpg","grid":"http://lain.bgm.tv/pic/crt/g/23/02/7977_prsn_eBB6P.jpg"},"comment":21,"collects":0,"info":{"name_cn":"佐伯俊","alias":{"0":"tosh","kana":"さえき しゅん","romaji":"Saeki Shun"},"gender":"男","birth":"1985年11月11日","bloodtype":"AB型","Twitter":"Syunsaeki","Pixiv id":"88701","Blog":"http://tosh2367.blog108.fc2.com/"},"jobs":["原作"]},{"id":1270,"url":"http://bgm.tv/person/1270","name":"米たにヨシトモ","name_cn":"米谷良知","role_name":"","images":{"large":"http://lain.bgm.tv/pic/crt/l/46/76/1270_prsn_NHCv1.jpg","medium":"http://lain.bgm.tv/pic/crt/m/46/76/1270_prsn_NHCv1.jpg","small":"http://lain.bgm.tv/pic/crt/s/46/76/1270_prsn_NHCv1.jpg","grid":"http://lain.bgm.tv/pic/crt/g/46/76/1270_prsn_NHCv1.jpg"},"comment":3,"collects":0,"info":{"birth":"1963-05-13","gender":"男","alias":{"romaji":"Yonetani Yoshitomo","jp":"米たにヨシトモ"},"name_cn":"米谷良知"},"jobs":["导演"]},{"id":7663,"url":"http://bgm.tv/person/7663","name":"加藤達也","name_cn":"加藤达也","role_name":"","images":{"large":"http://lain.bgm.tv/pic/crt/l/1e/4d/7663_prsn_HhYyY.jpg","medium":"http://lain.bgm.tv/pic/crt/m/1e/4d/7663_prsn_HhYyY.jpg","small":"http://lain.bgm.tv/pic/crt/s/1e/4d/7663_prsn_HhYyY.jpg","grid":"http://lain.bgm.tv/pic/crt/g/1e/4d/7663_prsn_HhYyY.jpg"},"comment":13,"collects":0,"info":{"name_cn":"加藤达也","alias":{"jp":"加藤達也","kana":"かとう たつや","romaji":"Kadokawa Shoten"},"gender":"男","birth":"1980年7月28日","source":"wikipedia"},"jobs":["音乐"]},{"id":3485,"url":"http://bgm.tv/person/3485","name":"下谷智之","name_cn":"","role_name":"","images":null,"comment":0,"collects":0,"info":{"alias":{"jp":"下谷智之","romaji":"Shitaya Tomoyuki"},"gender":"男"},"jobs":["人物设定"]},{"id":10229,"url":"http://bgm.tv/person/10229","name":"ヤスカワショウゴ","name_cn":"安川正吾","role_name":"","images":null,"comment":0,"collects":0,"info":{"name_cn":"安川正吾","alias":{"jp":"ヤスカワ正吾","kana":"やすかわ しょうご","romaji":"Yasukawa Shōgo"},"官方网站":"http://shogo.strikes.jp/","Twitter":"https://twitter.com/shogoy"},"jobs":["系列构成"]},{"id":24438,"url":"http://bgm.tv/person/24438","name":"都築裕佳子","name_cn":"都筑裕佳子","role_name":"","images":null,"comment":0,"collects":0,"info":{"name_cn":"都筑裕佳子"},"jobs":["作画监督"]},{"id":6400,"url":"http://bgm.tv/person/6400","name":"nano.RIPE","name_cn":"","role_name":"","images":{"large":"http://lain.bgm.tv/pic/crt/l/28/8f/6400_prsn_B6N6L.jpg?r=1424508825","medium":"http://lain.bgm.tv/pic/crt/m/28/8f/6400_prsn_B6N6L.jpg?r=1424508825","small":"http://lain.bgm.tv/pic/crt/s/28/8f/6400_prsn_B6N6L.jpg?r=1424508825","grid":"http://lain.bgm.tv/pic/crt/g/28/8f/6400_prsn_B6N6L.jpg?r=1424508825"},"comment":3,"collects":0,"info":{"alias":{"jp":"ナノライプ"},"birth":"2004年","成员":["きみコ（Vocal&amp;Guitar）","ササキジュン（Guitar）","アベノブユキ（Bass）","青山友樹（Drums）"],"source":"http://ja.wikipedia.org/wiki/Nano.RIPE"},"jobs":["主题歌演出"]},{"id":15581,"url":"http://bgm.tv/person/15581","name":"SCREEN mode","name_cn":"","role_name":"","images":{"large":"http://lain.bgm.tv/pic/crt/l/88/45/15581_prsn_ZL12i.jpg?r=1408846015","medium":"http://lain.bgm.tv/pic/crt/m/88/45/15581_prsn_ZL12i.jpg?r=1408846015","small":"http://lain.bgm.tv/pic/crt/s/88/45/15581_prsn_ZL12i.jpg?r=1408846015","grid":"http://lain.bgm.tv/pic/crt/g/88/45/15581_prsn_ZL12i.jpg?r=1408846015"},"comment":0,"collects":0,"info":{"alias":{"jp":"スクリーンモード"}},"jobs":["主题歌演出"]}]
     * topic : null
     * blog : null
     */

    private int id;
    private String url;
    private int type;
    private String name;
    private String name_cn;
    private String summary;
    private String air_date;
    private int air_weekday;
    /**
     * total : 302
     * count : {"1":2,"2":0,"3":0,"4":0,"5":10,"6":27,"7":120,"8":120,"9":16,"10":7}
     * score : 7.4
     */

    private RatingBean rating;
    private int rank;
    /**
     * large : http://lain.bgm.tv/pic/cover/l/57/35/160124_Yhhq2.jpg
     * common : http://lain.bgm.tv/pic/cover/c/57/35/160124_Yhhq2.jpg
     * medium : http://lain.bgm.tv/pic/cover/m/57/35/160124_Yhhq2.jpg
     * small : http://lain.bgm.tv/pic/cover/s/57/35/160124_Yhhq2.jpg
     * grid : http://lain.bgm.tv/pic/cover/g/57/35/160124_Yhhq2.jpg
     */

    private ImagesBean images;
    /**
     * wish : 102
     * collect : 26
     * doing : 1111
     * on_hold : 17
     * dropped : 9
     */

    private CollectionBean collection;
    private Object topic;
    private Object blog;
    /**
     * id : 640770
     * url : http://bgm.tv/ep/640770
     * type : 0
     * sort : 1
     * name : その箱に詰めるもの
     * name_cn : 盒中所装之物
     * duration : 00:24:38
     * airdate : 2016-07-02
     * comment : 60
     * desc :
     * status : Air
     */

    private List<EpsBean> eps;
    /**
     * id : 19453
     * url : http://bgm.tv/character/19453
     * name : 薙切 えりな
     * name_cn : 薙切绘里奈
     * role_name : 主角
     * images : {"large":"http://lain.bgm.tv/pic/crt/l/b6/40/19453_crt_Mfh8Y.jpg","medium":"http://lain.bgm.tv/pic/crt/m/b6/40/19453_crt_Mfh8Y.jpg","small":"http://lain.bgm.tv/pic/crt/s/b6/40/19453_crt_Mfh8Y.jpg","grid":"http://lain.bgm.tv/pic/crt/g/b6/40/19453_crt_Mfh8Y.jpg"}
     * comment : 11
     * collects : 43
     * info : {"name_cn":"薙切绘里奈","alias":{"jp":"薙切えりな","kana":"なきり えりな","romaji":"Nakiri erina"},"gender":"女"}
     * actors : [{"id":8138,"url":"http://bgm.tv/person/8138","name":"種田梨沙","images":{"large":"http://lain.bgm.tv/pic/crt/l/07/06/8138_prsn_PBCx5.jpg?r=1464881523","medium":"http://lain.bgm.tv/pic/crt/m/07/06/8138_prsn_PBCx5.jpg?r=1464881523","small":"http://lain.bgm.tv/pic/crt/s/07/06/8138_prsn_PBCx5.jpg?r=1464881523","grid":"http://lain.bgm.tv/pic/crt/g/07/06/8138_prsn_PBCx5.jpg?r=1464881523"}}]
     */

    private List<CrtBean> crt;
    /**
     * id : 9284
     * url : http://bgm.tv/person/9284
     * name : 附田祐斗
     * name_cn :
     * role_name :
     * images : {"large":"http://lain.bgm.tv/pic/crt/l/61/2d/9284_prsn_x0XQq.jpg","medium":"http://lain.bgm.tv/pic/crt/m/61/2d/9284_prsn_x0XQq.jpg","small":"http://lain.bgm.tv/pic/crt/s/61/2d/9284_prsn_x0XQq.jpg","grid":"http://lain.bgm.tv/pic/crt/g/61/2d/9284_prsn_x0XQq.jpg"}
     * comment : 6
     * collects : 0
     * info : {"alias":{"kana":"つくだ ゆうと"},"Twitter":"@tsukudayuto"}
     * jobs : ["原作"]
     */

    private List<StaffBean> staff;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_cn() {
        return name_cn;
    }

    public void setName_cn(String name_cn) {
        this.name_cn = name_cn;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAir_date() {
        return air_date;
    }

    public void setAir_date(String air_date) {
        this.air_date = air_date;
    }

    public int getAir_weekday() {
        return air_weekday;
    }

    public void setAir_weekday(int air_weekday) {
        this.air_weekday = air_weekday;
    }

    public RatingBean getRating() {
        return rating;
    }

    public void setRating(RatingBean rating) {
        this.rating = rating;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public ImagesBean getImages() {
        return images;
    }

    public void setImages(ImagesBean images) {
        this.images = images;
    }

    public CollectionBean getCollection() {
        return collection;
    }

    public void setCollection(CollectionBean collection) {
        this.collection = collection;
    }

    public Object getTopic() {
        return topic;
    }

    public void setTopic(Object topic) {
        this.topic = topic;
    }

    public Object getBlog() {
        return blog;
    }

    public void setBlog(Object blog) {
        this.blog = blog;
    }

    public List<EpsBean> getEps() {
        return eps;
    }

    public void setEps(List<EpsBean> eps) {
        this.eps = eps;
    }

    public List<CrtBean> getCrt() {
        return crt;
    }

    public void setCrt(List<CrtBean> crt) {
        this.crt = crt;
    }

    public List<StaffBean> getStaff() {
        return staff;
    }

    public void setStaff(List<StaffBean> staff) {
        this.staff = staff;
    }

    public static class RatingBean implements Serializable{
        private static final long serialVersionUID = -1399374979736250181L;
        private int total;
        /**
         * 1 : 2
         * 2 : 0
         * 3 : 0
         * 4 : 0
         * 5 : 10
         * 6 : 27
         * 7 : 120
         * 8 : 120
         * 9 : 16
         * 10 : 7
         */

        private CountBean count;
        private double score;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public CountBean getCount() {
            return count;
        }

        public void setCount(CountBean count) {
            this.count = count;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public static class CountBean implements Serializable{
            private static final long serialVersionUID = -4954935898198639482L;
            @SerializedName("1")
            private int value1;
            @SerializedName("2")
            private int value2;
            @SerializedName("3")
            private int value3;
            @SerializedName("4")
            private int value4;
            @SerializedName("5")
            private int value5;
            @SerializedName("6")
            private int value6;
            @SerializedName("7")
            private int value7;
            @SerializedName("8")
            private int value8;
            @SerializedName("9")
            private int value9;
            @SerializedName("10")
            private int value10;

            public int getValue1() {
                return value1;
            }

            public void setValue1(int value1) {
                this.value1 = value1;
            }

            public int getValue2() {
                return value2;
            }

            public void setValue2(int value2) {
                this.value2 = value2;
            }

            public int getValue3() {
                return value3;
            }

            public void setValue3(int value3) {
                this.value3 = value3;
            }

            public int getValue4() {
                return value4;
            }

            public void setValue4(int value4) {
                this.value4 = value4;
            }

            public int getValue5() {
                return value5;
            }

            public void setValue5(int value5) {
                this.value5 = value5;
            }

            public int getValue6() {
                return value6;
            }

            public void setValue6(int value6) {
                this.value6 = value6;
            }

            public int getValue7() {
                return value7;
            }

            public void setValue7(int value7) {
                this.value7 = value7;
            }

            public int getValue8() {
                return value8;
            }

            public void setValue8(int value8) {
                this.value8 = value8;
            }

            public int getValue9() {
                return value9;
            }

            public void setValue9(int value9) {
                this.value9 = value9;
            }

            public int getValue10() {
                return value10;
            }

            public void setValue10(int value10) {
                this.value10 = value10;
            }
        }
    }

    public static class ImagesBean implements Serializable{
        private static final long serialVersionUID = -8771480176322011797L;
        private String large;
        private String common;
        private String medium;
        private String small;
        private String grid;

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public String getCommon() {
            return common;
        }

        public void setCommon(String common) {
            this.common = common;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getGrid() {
            return grid;
        }

        public void setGrid(String grid) {
            this.grid = grid;
        }
    }

    public static class CollectionBean implements Serializable{
        private static final long serialVersionUID = 193259044520041933L;
        private int wish;
        private int collect;
        private int doing;
        private int on_hold;
        private int dropped;

        public int getWish() {
            return wish;
        }

        public void setWish(int wish) {
            this.wish = wish;
        }

        public int getCollect() {
            return collect;
        }

        public void setCollect(int collect) {
            this.collect = collect;
        }

        public int getDoing() {
            return doing;
        }

        public void setDoing(int doing) {
            this.doing = doing;
        }

        public int getOn_hold() {
            return on_hold;
        }

        public void setOn_hold(int on_hold) {
            this.on_hold = on_hold;
        }

        public int getDropped() {
            return dropped;
        }

        public void setDropped(int dropped) {
            this.dropped = dropped;
        }
    }

    public static class EpsBean implements Serializable{
        private static final long serialVersionUID = 7096218462913917695L;
        private int id;
        private String url;
        private int type;
        private double sort;
        private String name;
        private String name_cn;
        private String duration;
        private String airdate;
        private int comment;
        private String desc;
        /**WISH 想看 ,WATCHED 看过, DROP 抛弃, NA/TODOY 都是不可点*/
        private String status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public double getSort() {
            return sort;
        }

        public void setSort(double sort) {
            this.sort = sort;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName_cn() {
            return name_cn;
        }

        public void setName_cn(String name_cn) {
            this.name_cn = name_cn;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getAirdate() {
            return airdate;
        }

        public void setAirdate(String airdate) {
            this.airdate = airdate;
        }

        public int getComment() {
            return comment;
        }

        public void setComment(int comment) {
            this.comment = comment;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public static class CrtBean implements Serializable{
        private static final long serialVersionUID = 7482029494659203652L;
        private int id;
        private String url;
        private String name;
        private String name_cn;
        private String role_name;
        /**
         * large : http://lain.bgm.tv/pic/crt/l/b6/40/19453_crt_Mfh8Y.jpg
         * medium : http://lain.bgm.tv/pic/crt/m/b6/40/19453_crt_Mfh8Y.jpg
         * small : http://lain.bgm.tv/pic/crt/s/b6/40/19453_crt_Mfh8Y.jpg
         * grid : http://lain.bgm.tv/pic/crt/g/b6/40/19453_crt_Mfh8Y.jpg
         */

        private ImagesBean images;
        private int comment;
        private int collects;
        /**
         * name_cn : 薙切绘里奈
         * alias : {"jp":"薙切えりな","kana":"なきり えりな","romaji":"Nakiri erina"}
         * gender : 女
         */

        private InfoBean info;
        /**
         * id : 8138
         * url : http://bgm.tv/person/8138
         * name : 種田梨沙
         * images : {"large":"http://lain.bgm.tv/pic/crt/l/07/06/8138_prsn_PBCx5.jpg?r=1464881523","medium":"http://lain.bgm.tv/pic/crt/m/07/06/8138_prsn_PBCx5.jpg?r=1464881523","small":"http://lain.bgm.tv/pic/crt/s/07/06/8138_prsn_PBCx5.jpg?r=1464881523","grid":"http://lain.bgm.tv/pic/crt/g/07/06/8138_prsn_PBCx5.jpg?r=1464881523"}
         */

        private List<ActorsBean> actors;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName_cn() {
            return name_cn;
        }

        public void setName_cn(String name_cn) {
            this.name_cn = name_cn;
        }

        public String getRole_name() {
            return role_name;
        }

        public void setRole_name(String role_name) {
            this.role_name = role_name;
        }

        public ImagesBean getImages() {
            return images;
        }

        public void setImages(ImagesBean images) {
            this.images = images;
        }

        public int getComment() {
            return comment;
        }

        public void setComment(int comment) {
            this.comment = comment;
        }

        public int getCollects() {
            return collects;
        }

        public void setCollects(int collects) {
            this.collects = collects;
        }

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public List<ActorsBean> getActors() {
            return actors;
        }

        public void setActors(List<ActorsBean> actors) {
            this.actors = actors;
        }

        public static class ImagesBean implements Serializable{
            private static final long serialVersionUID = -4806839760405201262L;
            private String large;
            private String medium;
            private String small;
            private String grid;

            public String getLarge() {
                return large;
            }

            public void setLarge(String large) {
                this.large = large;
            }

            public String getMedium() {
                return medium;
            }

            public void setMedium(String medium) {
                this.medium = medium;
            }

            public String getSmall() {
                return small;
            }

            public void setSmall(String small) {
                this.small = small;
            }

            public String getGrid() {
                return grid;
            }

            public void setGrid(String grid) {
                this.grid = grid;
            }
        }

        public static class InfoBean implements Serializable{
            private static final long serialVersionUID = -434635837639008546L;
            private String name_cn;
            /**
             * jp : 薙切えりな
             * kana : なきり えりな
             * romaji : Nakiri erina
             */

            private AliasBean alias;
            private String gender;

            public String getName_cn() {
                return name_cn;
            }

            public void setName_cn(String name_cn) {
                this.name_cn = name_cn;
            }

            public AliasBean getAlias() {
                return alias;
            }

            public void setAlias(AliasBean alias) {
                this.alias = alias;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public static class AliasBean implements Serializable{
                private static final long serialVersionUID = 5718649594079125929L;
                private String jp;
                private String kana;
                private String romaji;

                public String getJp() {
                    return jp;
                }

                public void setJp(String jp) {
                    this.jp = jp;
                }

                public String getKana() {
                    return kana;
                }

                public void setKana(String kana) {
                    this.kana = kana;
                }

                public String getRomaji() {
                    return romaji;
                }

                public void setRomaji(String romaji) {
                    this.romaji = romaji;
                }
            }
        }

        public static class ActorsBean implements Serializable{
            private static final long serialVersionUID = 6299915871972180041L;
            private int id;
            private String url;
            private String name;
            /**
             * large : http://lain.bgm.tv/pic/crt/l/07/06/8138_prsn_PBCx5.jpg?r=1464881523
             * medium : http://lain.bgm.tv/pic/crt/m/07/06/8138_prsn_PBCx5.jpg?r=1464881523
             * small : http://lain.bgm.tv/pic/crt/s/07/06/8138_prsn_PBCx5.jpg?r=1464881523
             * grid : http://lain.bgm.tv/pic/crt/g/07/06/8138_prsn_PBCx5.jpg?r=1464881523
             */

            private ImagesBean images;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public ImagesBean getImages() {
                return images;
            }

            public void setImages(ImagesBean images) {
                this.images = images;
            }

            public static class ImagesBean implements Serializable{
                private static final long serialVersionUID = -8534159014030613270L;
                private String large;
                private String medium;
                private String small;
                private String grid;

                public String getLarge() {
                    return large;
                }

                public void setLarge(String large) {
                    this.large = large;
                }

                public String getMedium() {
                    return medium;
                }

                public void setMedium(String medium) {
                    this.medium = medium;
                }

                public String getSmall() {
                    return small;
                }

                public void setSmall(String small) {
                    this.small = small;
                }

                public String getGrid() {
                    return grid;
                }

                public void setGrid(String grid) {
                    this.grid = grid;
                }
            }
        }
    }

    public static class StaffBean implements Serializable{
        private static final long serialVersionUID = -3524824731139195533L;
        private int id;
        private String url;
        private String name;
        private String name_cn;
        private String role_name;
        /**
         * large : http://lain.bgm.tv/pic/crt/l/61/2d/9284_prsn_x0XQq.jpg
         * medium : http://lain.bgm.tv/pic/crt/m/61/2d/9284_prsn_x0XQq.jpg
         * small : http://lain.bgm.tv/pic/crt/s/61/2d/9284_prsn_x0XQq.jpg
         * grid : http://lain.bgm.tv/pic/crt/g/61/2d/9284_prsn_x0XQq.jpg
         */

        private ImagesBean images;
        private int comment;
        private int collects;
        private List<String> jobs;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName_cn() {
            return name_cn;
        }

        public void setName_cn(String name_cn) {
            this.name_cn = name_cn;
        }

        public String getRole_name() {
            return role_name;
        }

        public void setRole_name(String role_name) {
            this.role_name = role_name;
        }

        public ImagesBean getImages() {
            return images;
        }

        public void setImages(ImagesBean images) {
            this.images = images;
        }

        public int getComment() {
            return comment;
        }

        public void setComment(int comment) {
            this.comment = comment;
        }

        public int getCollects() {
            return collects;
        }

        public void setCollects(int collects) {
            this.collects = collects;
        }

        public List<String> getJobs() {
            return jobs;
        }

        public void setJobs(List<String> jobs) {
            this.jobs = jobs;
        }

        public static class ImagesBean implements Serializable{
            private static final long serialVersionUID = 9020492580362125355L;
            private String large;
            private String medium;
            private String small;
            private String grid;

            public String getLarge() {
                return large;
            }

            public void setLarge(String large) {
                this.large = large;
            }

            public String getMedium() {
                return medium;
            }

            public void setMedium(String medium) {
                this.medium = medium;
            }

            public String getSmall() {
                return small;
            }

            public void setSmall(String small) {
                this.small = small;
            }

            public String getGrid() {
                return grid;
            }

            public void setGrid(String grid) {
                this.grid = grid;
            }
        }
    }
}
