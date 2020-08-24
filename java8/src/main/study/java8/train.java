package study.java8;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 名称：
 * 功能：
 * 条件：
 * Created by wq on 2017/10/22.
 */
public class train {

        public static void main(String[] args) {
            List<Integer> opearte = new ArrayList<>();
            opearte.add(1); //加
            opearte.add(2); //减
            opearte.add(3); //乘
            opearte.add(4); //除

            BigDecimal one = BigDecimal.valueOf(0.8);
            BigDecimal two = BigDecimal.valueOf(3);
            BigDecimal tree = BigDecimal.valueOf(0.45);
            BigDecimal four = BigDecimal.valueOf(2);

            BigDecimal result = BigDecimal.ZERO;
            //3次运算，且不改变顺序
            for (int i = 0; i < 2; i++) {
                Integer thisOpereate = opearte.get(i);
                if(thisOpereate == 1){
                    result = one.add(two);
                }else if(thisOpereate ==2){
                    result = one.subtract(two);
                }else if(thisOpereate ==3){
                    result = one.multiply(two);
                }else if(thisOpereate ==4){
                    result = one.divide(two);
                }

                for (int j = 0; j < 2; j++) {
                    Integer twoOpereate = opearte.get(i);
                    if(twoOpereate == 1){
                        result = result.add(tree);
                    }else if(twoOpereate ==2){
                        result = result.subtract(tree);
                    }else if(twoOpereate ==3){
                        result = result.multiply(tree);
                    }else if(twoOpereate ==4){
                        result = result.divide(tree);
                    }
                    for (int k = 0; k < 2; k++) {
                        Integer threeOpereate = opearte.get(i);
                        if(threeOpereate == 1){
                            result = result.add(four);
                        }else if(threeOpereate ==2){
                            result = result.subtract(four);
                        }else if(threeOpereate ==3){
                            result = result.multiply(four);
                        }else if(threeOpereate ==4){
                            result = result.divide(four);
                        }
                        System.out.println("运算顺序："+thisOpereate+","+thisOpereate+","+threeOpereate+",结果："+result);
                    }
                }
            }
    }
}
