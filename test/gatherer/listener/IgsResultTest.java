package gatherer.listener;

import static gatherer.IgsConstants.*;

import org.junit.Test;

import play.test.UnitTest;

public class IgsResultTest extends UnitTest {

	private static final String[] testStrings = new String[] {
			"21 {Game 150: mstkigo vs orihiyoshi : Black resigns.}",
			"21 {Game 543: ks260820 vs MIYLI : W 69.5 B 79.0}",
			"21 {Game 165: cli vs boudu : Black forfeits on time.}", };

	@Test
	public void observerResult() throws Exception {

		WeiqiStorageMock storageMock = new WeiqiStorageMock() {
			@Override
			public void addResult(String server, String game, String result) {
				assertEquals("server:port", server);
				assertEquals("348", game);
				assertEquals("W 71.5 B 83.0", result);
			}
		};
		IgsResult cut = new IgsResult("server:port", storageMock);

		assertTrue("observed", cut.notify("9 {Game 348: weslie vs Yatsugatak : W 71.5 B 83.0}"));
		assertFalse(cut.notify(MOVE_LIST_OK));
		assertFalse(cut.retrieveResult);
	}

	@Test
	public void resultList() throws Exception {
		WeiqiStorageMock storageMock = new WeiqiStorageMock();
		IgsResult cut = new IgsResult("server:port", storageMock);
		for (String line : testStrings) {
			storageMock.resultCalled = false;
			assertTrue(line, cut.notify(line));
			assertFalse(cut.notify(OK));
			assertTrue(storageMock.resultCalled);
			assertFalse(cut.retrieveResult);
		}
	}

}
