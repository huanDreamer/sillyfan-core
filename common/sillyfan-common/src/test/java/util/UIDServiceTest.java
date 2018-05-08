package util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.sillyfan.common.util.UIDService;

public class UIDServiceTest {

    private static final Logger logger = LoggerFactory
            .getLogger(UIDServiceTest.class);

    @Test
    public void testDecode() {

        String bidid = "927849443443692290";

        UIDService.UIDToken token = UIDService.decode(bidid);

        logger.info("uid token info time[{}] shardId[{}] sequence[{}]",
                token.getTime(), token.getShardId(), token.getSequence());

    }

}
