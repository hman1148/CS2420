import java.util.Arrays;
import java.util.Random;

public class BinPacking {
    static int BINSIZE = 100;
    Integer [] requests; // changed to int
    int totalDisksScheduleWorstFit = 1;
    int totalDiskDecreasing = 1;
    boolean scheduleOfflineWorstFit = false;
    public BinPacking(int size){
        Random rand  = new Random(size); //Seed will cause the same sequence of numbers to b// e generated each test
        requests = new  Integer[size];

        for (int i=0; i < size; i++) {
            requests[i] = rand.nextInt(BINSIZE) + 1;
        }
        if (size <= 500 ) System.out.println("Size " + size + " " + Arrays.toString(requests));
    }

    public int scheduleWorstFit(int size) {
        LeftistHeap<Disk> heap = new LeftistHeap<>();
        int diskId = 0;
        // min bins is all the items of the array summed up and divided by 100 rounded up

        Disk intialDisk = new Disk(diskId ++, BINSIZE);
        intialDisk.add(requests[0]);
        heap.insert(intialDisk);

        for (int i = 1; i < requests.length; i++) {;

            Disk currentDisk = heap.findMax();

            if (currentDisk.getRemainingSpace() < requests[i]) { // currentDisk.getRemainingSpace < request[i]
                Disk anotherDisk = new Disk(diskId++, BINSIZE);
                anotherDisk.add(requests[i]);
                heap.insert(anotherDisk);
                this.totalDisksScheduleWorstFit += 1;

            } else {
                heap.deleteMax();
                currentDisk.add(requests[i]);
                heap.insert(currentDisk);
            }
        }
        this.message(size, heap, this.scheduleOfflineWorstFit);
        return this.totalDisksScheduleWorstFit;
    }


    private int calcMin(Integer[] requests) {
        int sum = 0;
        for (int i = 0; i < requests.length; i++) {
            sum += requests[i];
        }
        return (int) Math.ceil(sum / 100) + 1;
    }

    public int scheduleOfflineWorstFit(int size) {

        this.scheduleOfflineWorstFit = true;
        // sort requests in decending order then call the first method
        HeapSort<Integer> heapSort = new HeapSort<>();
        heapSort.sort(requests);
        LeftistHeap<Disk> heap = new LeftistHeap<>();
        int diskId = 0;
        // min bins is all the items of the array summed up and divided by 100 rounded up

        Disk intialDisk = new Disk(diskId ++, BINSIZE);
        intialDisk.add(requests[0]);
        heap.insert(intialDisk);

        for (int i = 1; i < requests.length; i++) {;
            Disk currentDisk = heap.findMax();

            if (currentDisk.getRemainingSpace() < requests[i]) { // currentDisk.getRemainingSpace < request[i]
                Disk anotherDisk = new Disk(diskId++, BINSIZE);
                anotherDisk.add(requests[i]);
                heap.insert(anotherDisk);
                this.totalDiskDecreasing += 1;

            } else {
                heap.deleteMax();
                currentDisk.add(requests[i]);
                heap.insert(currentDisk);
            }
        }

        this.message(size, heap, this.scheduleOfflineWorstFit);

        return this.totalDiskDecreasing;
    }

    private void message(int size, LeftistHeap<Disk> heap, boolean change) {
        int min = this.calcMin(requests);

        if (size <= 20) {
            if (!change) {
                System.out.println("OnLine worst Fit Bin Packing Yields " + this.totalDisksScheduleWorstFit + " (requestCt = " + size + ") Minimum number of bins: " + min);
                heap.printHeap();
            } else {
                System.out.println("Decreasing Worst Fit Bin Packing Yields " + this.totalDiskDecreasing + " (requestCt = " + size + ") Minimum number of bins: " + min);
                heap.printHeap();
            }
        } else {
            if (!change) {
                System.out.println("OnLine worst Fit Bin Packing Yields " + this.totalDisksScheduleWorstFit + " (requestCt = " + size + ") Minimum number of bins: " + min);
            } else {
                System.out.println("Decreasing Worst Fit Bin Packing Yields " + this.totalDiskDecreasing + " (requestCt = " + size + ") Minimum number of bins: " + min);
            }
        }

    }

    public void bonusMethod(int size) {
        int totalDisks = 0;
//        BinaryTree tree = new B
        //create a binary search tree and add disk to it. Search the binary search tree to find a disk that has enough space to fit n amount of data
        // have the search return the smallest disk that can fit the data.
        BinarySearchTree<Disk> binarySearch = new BinarySearchTree<>();
        int diskId = 0;
        // min bins is all the items of the array summed up and divided by 100 rounded up
        Disk intialDisk = new Disk(diskId ++, BINSIZE);
        intialDisk.add(requests[0]);
        binarySearch.insert(intialDisk);
        int min = calcMin(requests);

        for (int i = 1; i < requests.length; i++) {;

            Disk ampleDisk = this.getOptimalDisk(binarySearch, requests[i]);

            if (ampleDisk != null) {
                binarySearch.remove(ampleDisk);
                ampleDisk.add(requests[i]);
                binarySearch.insert(ampleDisk);

            } else {
                Disk anotherDisk = new Disk(diskId ++, BINSIZE);
                anotherDisk.add(requests[i]);
                binarySearch.insert(anotherDisk);
                totalDisks += 1;
            }
        }
        System.out.println("With a Binary Search Tree our total disks are: " + totalDisks + " given " + size + " requests. Our number of bins is: " + min);
    }


    private Disk getOptimalDisk(BinarySearchTree<Disk> tree, int size) {
        BinarySearchTree.BinaryNode<Disk> current = tree.getRoot();
        return this.getOptimalDisk(current, size);
    }

    private Disk getOptimalDisk(BinarySearchTree.BinaryNode<Disk> node, int size) {

        if (node == null) {
            return null;
        }

        if (node.element.remainingSpace > size) {
            Disk temp = getOptimalDisk(node.left, size);

            if (temp == null) {
                return node.element;
            }
            return temp;
        } else if(node.element.remainingSpace < size) {
            return getOptimalDisk(node.right, size);
        } else {
            return node.element;
        }
    }


    public static void main (String[] args) {
        int [] fileSizes = {10, 20, 100, 500, 10000, 100000};

        for (int size : fileSizes){
            BinPacking b = new BinPacking(size);
            double scheduleWorst = b.scheduleWorstFit(size);
            double scheduleOffline = b.scheduleOfflineWorstFit(size);
            int percentage = (int) ((scheduleOffline / scheduleWorst) * 100);

            System.out.println("The difference is: " + (scheduleWorst - scheduleOffline) + " The ratio between the two is: " + percentage + "%");
            System.out.print("Binary Search: ");
            b.bonusMethod(size);
            System.out.println();
        }

    }}
