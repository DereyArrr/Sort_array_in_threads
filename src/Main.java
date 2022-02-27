/*Дан большой массив (~50к элементов), его надо отсортировать неэффективным способом(пузырьком)
 и сортировку производить разделением исходного массива на 2 части (задача со *: кол-во частей неизвестно).
 Алгоритм реализовать с потощью потоков: каждую часть сортировать в отдельном потоке,
 в конце выполнить слияние частей в один массив*/

//класс сортировки
class Sort {
    public static void bubbleSort(int[] a) {
        boolean sorted = false;
        int temp;
        while (!sorted) {
            sorted = true;
            for (int i = 0; i < a.length - 1; i++) {
                if (a[i] > a[i+1]) {
                    temp = a[i];
                    a[i] = a[i+1];
                    a[i+1] = temp;
                    sorted = false;
                }
            }
        }
    }

}
//класс потока
class Thread1 extends Thread
{
    private int[] Array;
    //конструктор
    public Thread1(int[] Array) {
        // Сохранить внешний массив
        this.Array = Array;
    }
    public void run()	//Этот метод будет выполнен в побочном потоке
    {
        Sort.bubbleSort(Array);
    }
}


public class Main {
    public static void main(String[] args) {
        //создаем начальный массив и заполняем его рандомными числами от 0 до 20
        int k = 15;
        int[] myArray = new int[k];
        for (int i = 0; i < myArray.length; i++) {
            myArray[i] = ((int) (Math.random() * 20));
            System.out.print(myArray[i]);//выводим начальный массив
            System.out.print(" ");
        }
       // разбиваем массив на 2 части
            int k1 = k/2;
        System.out.print("      ");
        System.out.print(k1);
            int k2 = k-k1;
        System.out.print("      ");
        System.out.print(k2);
        int[] Array1 = new int[k1];
        int[] Array2 = new int[k2];
        int index1 = 0;
        int index2 = 0;
        for (int i = 0; i < myArray.length; i++) {
            if(i<k1) {
                Array1[index1]=myArray[i];
                index1++;
            }else{
                Array2[index2]=myArray[i];
                index2++;
            }
        }
        //выводим разделенные массивы
        System.out.print("    ");
        for (int i = 0; i < Array1.length; i++) {
            System.out.print(Array1[i]);
            System.out.print(" ");
        }
        System.out.print("    ");
        for (int i = 0; i < Array2.length; i++) {
            System.out.print(Array2[i]);
            System.out.print(" ");
        }
        //сортируем обе части в 2х потоках
        Thread1 t1 = new Thread1(Array1);
        Thread1 t2 = new Thread1(Array2);

        t1.start();
        t2.start();
        //Дождаться завершения потоков t1, t2, чтобы получить корректный результат
        try {
            t1.join();
            t2.join();
        }
        catch (InterruptedException e) {
            // Если ошибка, то вывести сообщение
            System.out.println("Error: " + e.getMessage());
            return;
        }
//выводим 2 отсортированные части
        System.out.print("      ");
        for (int i = 0; i < Array1.length; i++) {
            System.out.print(Array1[i]);
            System.out.print(" ");
        }

        System.out.print("    ");
        for (int i = 0; i < Array2.length; i++) {
            System.out.print(Array2[i]);
            System.out.print(" ");
        }
//слияние в общий массив
        int[] result = new int[k];
        int i = 0, j = 0, r = 0;
        while (i < k1 && j < k2) {
            if (Array1[i] < Array2[j]) {
                result[r] = Array1[i];
                i++;
            } else {
                result[r] = Array2[j];
                j++;
            }
            r++;
        }
        if (i < k1) {
            System.arraycopy(Array1, i, result, r, (k1 - i));//копируем из 1 массива начиная с позиции i в результ. массив начиная с позиции r осташиеся эл-ты в кол-ве (k1 - i)
        }
        if (j < k2) {
            System.arraycopy(Array2, j, result, r, (k2 - j));//аналогично предыдущему
        }
//выводим отсортированный массив
        System.out.print("      ");
        for (int c = 0; c < k; c++) {
            System.out.print(result[c]);
            System.out.print(" ");
        }
    }
}
