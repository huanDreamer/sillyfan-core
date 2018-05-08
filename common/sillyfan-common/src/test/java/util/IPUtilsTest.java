package util;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.sillyfan.common.util.IPUtils;

import java.util.ArrayList;
import java.util.List;

public class IPUtilsTest {

    private static final Logger logger = LoggerFactory
            .getLogger(IPUtilsTest.class);

    /**
     * ipv4 score算法正确性验证
     */
    @Test
    public void testIpv4SocreCorrectness() {
        // "10.20.30.40" 正确的score为 169090600
        long score = IPUtils.ipv4Socre("10.20.30.40");

        Assert.assertEquals(169090600L, score);
    }

    /**
     * 测试score算法效率
     */
    @Test
    public void testIpv4SocrePerfromance() {

        // 10万IP数据 = 100,000
        List<String> ips = new ArrayList<>(100000);
        StringBuilder ipbuilder = new StringBuilder(15);

        for (int i = 0; i < 100000; i++) {
            ipbuilder.setLength(0);

            ipbuilder.append(RandomUtils.nextInt(0, 256));
            ipbuilder.append(".");
            ipbuilder.append(RandomUtils.nextInt(0, 256));
            ipbuilder.append(".");
            ipbuilder.append(RandomUtils.nextInt(0, 256));
            ipbuilder.append(".");
            ipbuilder.append(RandomUtils.nextInt(0, 256));

            ips.add(ipbuilder.toString());
        }

        logger.info("get first ip[{}] form ip pool.", ips.get(0));
        logger.info("get second ip[{}] form ip pool.", ips.get(1));
        logger.info("get last ip[{}] form ip pool.", ips.get(ips.size() - 1));

        long start = System.currentTimeMillis();

        for (String ip : ips) {
            IPUtils.ipv4Socre(ip);
        }

        logger.info("calculate {} ip used {} ms.", ips.size(),
                (System.currentTimeMillis() - start));
    }

}
