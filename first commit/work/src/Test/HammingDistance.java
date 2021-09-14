package Test;

/**
 * @author yzq
 * @ClassName HammingDistance
 * @Description 传入两个simhash值，计算其HammingDistance
 * @date 2021/9/11 6:12
 * @Version 1.0
 */
public class HammingDistance {

    public static int getHammingDistance(String simHash1, String simHash2){
        int D = 0; //距离
        if (simHash1.length() != simHash2.length())
            D = -1;
        else {
            //bitset不行
            for (int i=0; i<simHash1.length(); i++){
                //按位比较
                if (simHash1.charAt(i)!=simHash2.charAt(i))
                    D++;
            }
        }
        return D;
    }

    //求相似度

    public static double getSimilarity(String simHash1, String simHash2){
        //海明距离
        int dis = getHammingDistance(simHash1,simHash2);
        //System.out.println(dis);
        //一般来说，海明距离小于3即可认为文本之间相似度高，
        //使用Jaccard进行相似度计算, 因为dis就是simhash之间不同，这个就是差集的大小，
        //交集
        int intersection = simHash1.length()-dis;
        //并集
        int union = dis+simHash1.length();
        double sim = 0.01*(100*intersection/union);

        return sim;
    }
}
