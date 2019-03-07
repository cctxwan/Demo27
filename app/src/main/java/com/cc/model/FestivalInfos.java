package com.cc.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 节日对象
 */
public class FestivalInfos {

    //静态参数
    public static FestivalInfos mInstance;

    //这个是所有节日的
    List<Festival> infos =  new ArrayList<Festival>();

    //这个是所有节日下面的所有短信
    List<SMSdetail> smSdetails = new ArrayList<>();

    //在无参的构造方法中去填充对象
    public FestivalInfos() {
        infos.add(new Festival(1, "元旦节"));
        infos.add(new Festival(2, "元宵节"));
        infos.add(new Festival(3, "清明节"));
        infos.add(new Festival(4, "五一节"));
        infos.add(new Festival(5, "端午节"));
        infos.add(new Festival(6, "中秋节"));
        infos.add(new Festival(7, "国庆节"));
        infos.add(new Festival(8, "重阳节"));
        infos.add(new Festival(9, "春节"));


        smSdetails.add(new SMSdetail(1, 1, "感恩的心画出一个完美的元旦，欢笑泪水都飞上了记忆的彩虹，我们站在桥下握手成功，吉祥如意映着好运相从。祝福你新年新气象，新年新笑容，天天开心，日日轻松。"));
        smSdetails.add(new SMSdetail(2, 1, "岁末总结，交给你审核。对我的祝福要负责，愿所有的幸福为你飘落，美丽的焰火燃起的那一刻，元旦轻轻来过，祝你元旦快乐！"));
        smSdetails.add(new SMSdetail(3, 1, "世界一片茫茫白雪，心中一份浓浓真情，白雪装饰世界的美丽，真情为想念传递问候，辞旧迎新时，送上一句元旦快乐，祝你事事如意。"));
        smSdetails.add(new SMSdetail(4, 1, "指尖按键，短信传递。元旦将至，祝福送上。身安康，体健壮。财源滚滚福禄降。事如意，人吉祥，幸福快乐伴身旁。祝您笑口常开每一天。"));
        smSdetails.add(new SMSdetail(5, 1, "缕缕牵挂，让白云替我传达；声声祝福，由短信帮我送出。虽然总是茫茫碌碌不常相聚，心里却从不曾把你忘记。在此问候你：最近还好吧！元旦快乐！"));
        smSdetails.add(new SMSdetail(6, 1, "元旦伊始，愿好事接二连三，四季愉快，生活五艳六色，心情七彩缤纷，然八时烦恼抛在九宵云外，愿所有人都十全十美！"));
        smSdetails.add(new SMSdetail(7, 1, "元旦到，祝你拥有健康的BODY，用不完的MONEY，遇到完美的HONEY，生一个可爱的BABY，心情一直SUNNY，日子过得永远HAPPY！"));
    }

    /**
     * 通过id查询当前节日对象
     * @param id
     * @return
     */
    public List<SMSdetail> getSMSbyid(int id){
        List<SMSdetail> sms = new ArrayList<>();
        for (SMSdetail smSdetail : smSdetails){
            if(smSdetail.getFestivalid() == id){
                sms.add(smSdetail);
            }
        }
        return sms;
    }

    /**
     *通过id查询当前节日的短信详情对象
     * @param id
     * @return
     */
    public SMSdetail getBySMSId(int id){
        for (SMSdetail smSdetail  : smSdetails){
            if(smSdetail.getId() == id){
                return smSdetail;
            }
        }
        return null;
    }

    /**
     * 提供一个公用的get方法
     * @return
     */
    public List<Festival> getInfos(){
        return new ArrayList<Festival>(infos);
    }

    /**
     * 提供一个根据id查询的方法
     * @param id
     * @return
     */
    public Festival getFestivalById(int id){
        for (Festival smSblessInfos : infos){
            if(smSblessInfos.getId() == id){
                return smSblessInfos;
            }
        }
        return null;
    }

    /**
     * 实例化当前对象
     * @return
     */
    public static FestivalInfos getmInstance(){
        //提升效率。防止重复创建
        if(mInstance == null){
            //同步锁   防止多线程进入
            synchronized (FestivalInfos.class){
                //实例化对象
                if(mInstance == null)
                    mInstance = new FestivalInfos();
            }
        }
        return mInstance;
    }

}
