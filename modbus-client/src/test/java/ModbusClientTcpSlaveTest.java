import com.github.sunjx.modbus.client.ModbusClient4TcpMaster;
import com.github.sunjx.modbus.client.ModbusClientTcpFactory;

/**
 * @version V1.0
 * @author: sunjx
 * @create: 2019/12/3
 */
public class ModbusClientTcpSlaveTest {

    public static void main(String[] args) throws Exception {
        ModbusClientHandlerTest handlerTest = new ModbusClientHandlerTest("BG_A4_WK");
        ModbusClientTcpFactory.getInstance().createClient4Slave("localhost", 502, handlerTest);
    }
}
