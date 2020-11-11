package week04;

public class DemoJoin {
	public static void main(String[] args) {
		long start = System.currentTimeMillis();

		final int[] result = new int[1];
        // �����ﴴ��һ���̻߳��̳߳أ�
        // �첽ִ�� ���淽��
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				result[0] = sum();//���ǵõ��ķ���ֵ
			}
		});
		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// ȷ��  �õ�result �����
		System.out.println("�첽������Ϊ��" + result[0]);
		System.out.println("ʹ��ʱ�䣺" + (System.currentTimeMillis() - start) + " ms");
		// Ȼ���˳�main�߳�

	}

	private static int sum() {
		return fibo(36);
	}

	private static int fibo(int a) {
		if (a < 2)
			return 1;
		return fibo(a - 1) + fibo(a - 2);
	}
}
