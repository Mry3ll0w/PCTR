class PCMonitor {
	final int N = 5;
	int Oldest = 0, Newest = 0;
    volatile int Count = 0;
	int Buffer[] = new int[N];

    //Ver el pseudo en los apuntes para vefrificar
	synchronized void Append(int V) {//Signal
        while (Count == N)
        try {
            wait();
        } catch (InterruptedException e) {}
		Buffer[Newest] = V;
		Newest = (Newest + 1) % N;
		Count = Count + 1;
		notifyAll();//Siempre hay que realizar el notifyAll pq no tenemos variables de cond, tenemos cola wait set
	}

	synchronized int Take() {//Wait
        int temp;
        while (Count == 0)
        try {
            wait();
        } catch (InterruptedException e) {}
		temp = Buffer[Oldest];
		Oldest = (Oldest + 1) % N;
		Count = Count - 1;
		notifyAll();
		return temp;
	}
}