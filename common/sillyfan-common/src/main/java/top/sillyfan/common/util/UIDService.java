package top.sillyfan.common.util;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

/**
 * 生成系统中唯一ID <br/>
 * https://github.com/Predictor/javasnowflake/tree/master/IdGenerator/src/org/predictor/idgenerator
 */
public class UIDService {

	public static class UIDToken {

		private String time;

		private String shardId;

		private String sequence;

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public String getShardId() {
			return shardId;
		}

		public void setShardId(String shardId) {
			this.shardId = shardId;
		}

		public String getSequence() {
			return sequence;
		}

		public void setSequence(String sequence) {
			this.sequence = sequence;
		}
	}

	// id format =>
	// |timestamp |datacenter | sequence|
	// |41 |10 | 12 |
	private static final long SEQUENCE_BITS = 12L;
	private static final long SEQUENCE_MASK = 0xFFF;
	private static final long SHARD_ID_BITS = 10L;
	private static final long SHARD_ID_MASK = 0x3FF000;

	private static final long SHARD_ID_SHIFT = SEQUENCE_BITS;
	private static final long TIMESTAMP_SHIFT = SEQUENCE_BITS + SHARD_ID_BITS;

	private static final long EPOCH = 1288834974657L;// 1502276183000l;
	// private static final long MAX_SHARD_ID = -1L ^ (-1L << SHARD_ID_BITS);
	private static final long MAX_SEQUENCE = 4096L;

	private static Sequencer incr = new Sequencer();

	/**
	 * 生成唯一ID <br/>
	 * shardId用于多台服务唯一标识
	 *
	 * @param shardId
	 *            多台服务唯一标识
	 * @return String
	 */
	public static String generate(Integer shardId) {

		long result = (System.currentTimeMillis() - EPOCH) << TIMESTAMP_SHIFT;
		result = result | (shardId << SHARD_ID_SHIFT);
		result = result | (incr.next() % MAX_SEQUENCE);

		return String.valueOf(result);
	}

	/**
	 * 解码UID
	 *
	 * @param uid
	 * @return UIDToken
	 */
	public static UIDToken decode(String uid) {

		if (StringUtils.isBlank(uid)) {
			return null;
		}

		long value = Long.valueOf(uid);

		long sequence = SEQUENCE_MASK & value;
		long shardId = (SHARD_ID_MASK & value) >> SHARD_ID_SHIFT;
		long timestamp = ((~(SHARD_ID_MASK | SEQUENCE_MASK))
				& value) >> TIMESTAMP_SHIFT;
		String datetime = new DateTime(timestamp + EPOCH)
				.toString("yyyy-MM-dd HH:mm:ss.SSS");

		UIDToken token = new UIDToken();

		token.setSequence(String.valueOf(sequence));
		token.setShardId(String.valueOf(shardId));
		token.setTime(datetime);

		return token;
	}

}
