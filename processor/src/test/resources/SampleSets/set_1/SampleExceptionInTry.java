import edu.gvsu.prestongarno.testing.util.OnCloseResourceException;
import edu.gvsu.prestongarno.testing.util.TestAcloseable;

public class SampleExceptionInTry {

	public Object _ASSERT_NOT_NULL = null;

	public void INVOKE_ME() throws OnCloseResourceException {

		try (TestAcloseable something = new TestAcloseable(true, false))
		{
			_ASSERT_NOT_NULL = something;
			something.doRiskyThings();
		}
	}

}