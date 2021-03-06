package study.multithreading.masterSlave;

import java.util.Map;

/**
 * 名称：
 * 功能：
 * 条件：
 * Created by wq on 2017/9/17.
 */
public class LocationRequestFactory extends AbstractRequestFactory {

    public LocationRequestFactory(Map<String, Integer> respDelayConf,
                                  int timestampMaxOffset) {
        super(respDelayConf, timestampMaxOffset);
    }

    @Override
    public SimulatedRequest newRequest() {
        return new SimulatedRequest() {

            @Override
            public void printLogs(Logger logger) {
                final int internalDelay = genInternalDelay();
                LogEntry entry = new LogEntry();
                long timeStamp = nextRequestTimestamp();
                entry.timeStamp = timeStamp;
                entry.recordType = "request";

                entry.interfaceType = "REST";
                entry.interfaceName = "Location";
                entry.operationName = "getLocation";
                entry.srcDevice = "OSG";
                entry.dstDevice = "ESB";

                int traceId = seq.getAndIncrement();
                int originalTId = traceId;

                entry.traceId = "0020" + traceIdFormatter.format(traceId);

                logger.printLog(entry);

                timeStamp += internalDelay;
                entry.timeStamp = timeStamp;
                entry.recordType = "request";
                entry.operationName = "getLocation";
                entry.srcDevice = "ESB";
                entry.dstDevice = "NIG";
                traceId = traceId + 1;
                entry.traceId = "0021" + traceIdFormatter.format(traceId);
                logger.printLog(entry);

                timeStamp += genResponseDelay("NIG");
                entry.timeStamp = timeStamp;
                entry.recordType = "response";
                entry.operationName = "getLocationRsp";
                entry.srcDevice = "NIG";
                entry.dstDevice = "ESB";
                traceId = traceId + 3;
                entry.traceId = "0021" + traceIdFormatter.format(traceId);
                logger.printLog(entry);

                timeStamp += internalDelay;
                entry.timeStamp = timeStamp;
                entry.recordType = "response";
                entry.operationName = "getLocationRsp";
                entry.srcDevice = "ESB";
                entry.dstDevice = "OSG";
                entry.traceId = "0020" + traceIdFormatter.format(originalTId + 2);
                logger.printLog(entry);

            }

            @Override
            public String getInterfaceName() {
                return "Location";
            }

        };
    }

}
