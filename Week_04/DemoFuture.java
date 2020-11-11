package week04;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DemoFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        long start = System.currentTimeMillis();
       
        // �����ﴴ��һ���̻߳��̳߳أ�
        ExecutorService executor = Executors.newCachedThreadPool();
        
        // �첽ִ�� ���淽��
        Future<Integer> future = executor.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return sum();
            }
        });
        
        
        int result = future.get();//���ǵõ��ķ���ֵ
        // ȷ��  �õ�result �����
        System.out.println("�첽������Ϊ��" + result);

        System.out.println("ʹ��ʱ�䣺" + (System.currentTimeMillis() - start) + " ms");

        // Ȼ���˳�main�߳�
        executor.shutdown();
    }
    private static int sum() {
        return fibo(36);
    }
    
    private static int fibo(int a) {
        if ( a < 2) 
            return 1;
        return fibo(a-1) + fibo(a-2);
    }
}
