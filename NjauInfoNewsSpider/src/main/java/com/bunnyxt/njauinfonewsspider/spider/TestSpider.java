package com.bunnyxt.njauinfonewsspider.spider;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.bunnyxt.njauinfonewsspider.model.News;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

public class TestSpider {

    public static void main(String[] args) {

        System.out.println("Test Spider");

        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            try {
                News n = new News();
                n.setTitle("标题");
                n.setAuthor("bunnyxt");
                Date d = new Date();
                d.setYear(2019 - 1900);
                d.setMonth(7 - 1);
                d.setDate(12);
                d.setHours(19);
                d.setMinutes(12);
                d.setSeconds(34);
                n.setCtime(d);
                n.setContent("<p> &nbsp; &nbsp;12月3日，徐焕良教授受邀访问西班牙马德里理工大学。期间与马德里理工大学电信和电子工程学院（Departamento de Ingeniería Telemática y Electrónica (DTE)）的 José Fernán Martínez &nbsp;教授 课题组进行了深入交流。徐焕良教授与José Fernán Martínez &nbsp;教授 相互介绍了学校和学科发展，参观了马德里理工大学的通信历史博物馆，并介绍了各自的研究课题和热点问题。</p><p> <img src=\"/ueditor/net/upload/2018-12-11-598c890a1b-0829-4ad1-9659-27ea2872e193.jpg\" style=\"float:none;\" title=\"图片1.jpg\" border=\"0\" hspace=\"0\" vspace=\"0\"></p><p> &nbsp; &nbsp;徐焕良教授介绍了信息科技学院在智慧农业、物联网、食品安全溯源、表型信息技术等方面的研究情况。 José Fernán Martínez &nbsp;教授介绍了在下一代网络结构和服务，AFarCloud项目以及IOT等的研究及应用。双方在智慧农业，物联网，表型信息技术等研究热点和应用等方面进行了深入的交流。最后在人才互访、学术交流与项目合作等方面交换了意见，并签订了合作备忘录。</p><p> &nbsp; &nbsp;12月4～6号，徐焕良教授和袁培森博士参加了IEEE国际生物信息学/生物医学BIBM（International Conference on Bioinformatics &amp; Biomedicine） 2018国际会议，BIBM被CCF认定的B类会议。该届会议由西班牙马德里卡洛斯三世大学主办，袁培森在该会议上发表论文题为“Chrysanthemum Abnormal Petal Type Classification using Random Forest and Over-sampling”，并做了会议报告。</p><p><br></p>");
                n.setTid(1);
                n.setIid(3);
                n.setSid(15);
                n.setNid(2401);
                n.setPid(1);
                System.out.println(n);
                sqlSession.insert("NewsDaoMapper.insertNews", n);
                sqlSession.commit();
                List<News> newsList = sqlSession.selectList("NewsDaoMapper.queryNewsAll");
                System.out.println(newsList);
            } finally {
                sqlSession.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
