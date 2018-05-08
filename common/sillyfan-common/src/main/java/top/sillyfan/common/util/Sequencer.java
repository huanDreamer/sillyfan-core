package top.sillyfan.common.util;

import java.util.concurrent.atomic.AtomicLong;

/**
 * From
 * https://github.com/twitter/cloudhopper-commons/blob/master/ch-commons-util/src/main/java/com/cloudhopper/commons/util/Sequencer.java
 */
public class Sequencer {

	private AtomicLong sequenceNumber;

	/**
	 * Constructs a new instance of <code>Sequencer</code> a default starting
	 * sequence number of 0.
	 */
	public Sequencer() {
		sequenceNumber = new AtomicLong(0);
	}

	/**
	 * Constructs a new instance of <code>Sequencer</code> with the specified
	 * starting value.
	 */
	public Sequencer(long startingValue) {
		sequenceNumber = new AtomicLong(startingValue);
	}

	public long next() {
		long seqNum = sequenceNumber.getAndIncrement();
		// check if this value is getting close to overflow?
		if (seqNum > Long.MAX_VALUE - 1000000000) {
			sequenceNumber.set(0);
		}
		return seqNum;
	}
}
