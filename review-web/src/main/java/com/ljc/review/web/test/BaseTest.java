package com.ljc.review.web.test;


import org.junit.Test;

public class BaseTest {

    /*随机生成 Salary {name, baseSalary, bonus  }的记录，如“wxxx,10,1”，每行一条记录，总共1000万记录，写入文本文件（UFT-8编码），
      然后读取文件，name的前两个字符相同的，其年薪累加，比如wx，100万，3个人，最后做排序和分组，输出年薪总额最高的10组：
         wx, 200万，10人
         lt, 180万，8人
         ....
      name 4位a-z随机，    baseSalary [0,100]随机 bonus[0-5]随机 年薪总额 = baseSalary*13 + bonus

      请努力将程序优化到5秒内执行完*/
    @Test
    public void test() {

    }

}
