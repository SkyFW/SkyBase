package org.skyfw.base.collection;

import org.apache.commons.collections4.comparators.NullComparator;
import org.jetbrains.annotations.NotNull;
import org.skyfw.base.classes.comparator.TNullComparator;
import org.skyfw.base.datamodel.*;
import org.skyfw.base.exception.TException;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class TDataTreeSet<E extends TDataModel> extends AbstractSet<E>
        implements TSortedDataSet<E>, Cloneable, java.io.Serializable {

   TFieldDescriptor sortFieldDescriptor;

   private static final NullComparator nullComparator= new TNullComparator();

    /**
     * The backing map.
     */
    private transient NavigableMap<Object,Stack<E>> map;

    /**
     * The number of entries in the DataSet
     */
    private transient int size = 0;

    /**
     * The number of structural modifications to the DataSet.
     */
    private transient int modCount = 0;


    public TDataTreeSet(TFieldDescriptor sortFieldDescriptor) {
        //super();
        this.sortFieldDescriptor= sortFieldDescriptor;
        this.map= new TreeMap<>(nullComparator);
    }

    public TDataTreeSet(TFieldDescriptor sortFieldDescriptor, Collection<? extends E> c) {
        this(sortFieldDescriptor);
        if (c != null)
            addAll(c);
    }

    public TDataTreeSet(TFieldDescriptor sortFieldDescriptor, SortedMap<Object, Stack<E>> sortedMap) {

        // >>> It will internally call "buildFromSorted()" so being sorted will be considered
        // and the performance must be optimized.
        //ToDo: Performance check
        this.map= new TreeMap<>(sortedMap/*, nullComparator*/);
        this.sortFieldDescriptor= sortFieldDescriptor;
        refreshTheClone(this);
    }

    private static void setNullAlternative(TFieldDescriptor fieldDescriptor){

        //if
        //NULL
    }

    /*public TDataTreeSet(SortedSet<E> s) {
        this(s.comparator());
        addAll(s);
    }*/


    public Iterator<E> iterator() {
        //return map.navigableKeySet().iterator();
        return new TDataTreeSetIterator(this.map.entrySet());
    }

    /**
     * Returns an iterator over the elements in this set in descending order.
     *
     * @return an iterator over the elements in this set in descending order
     * @since 1.6
     */
    public Iterator<E> descendingIterator() {
        //return map.descendingKeySet().iterator();
        return new TDataTreeSetIterator(this.map.descendingMap().entrySet());
    }

    /**
     * @since 1.6
     */
    public NavigableSet<E> descendingSet() {
        //return new TreeSet<E>(map.descendingMap().values());
        return null;
    }


    public int size() {
        //return map.size();
        return this.size;
    }


    public boolean isEmpty() {
        //return map.isEmpty();
        return size == 0;
    }


    public boolean contains(Object fieldValue) {
        return map.containsKey(fieldValue);
    }


    public boolean add(@NotNull E dm) {

        try {
            boolean replaced= true;
            Object fieldValue = TDataModelHelper.getFieldValue(dm, this.sortFieldDescriptor);
            Stack<E> stack = this.map.get(fieldValue);
            if (stack == null){
                stack= new Stack<>();
                map.put(fieldValue, stack);
                replaced= false;
            }
            // >>> Always returns true :/
            // ToDo: Does it violates the collections contract ?
            /*boolean replaced= */stack.add(dm);

            this.size++;
            this.modCount++;
            return replaced;
        }catch (TException e){
            e.log();
        }
        return false;
    }




    public boolean remove(E dm) {

        try {
            Object fieldValue = TDataModelHelper.getFieldValue(dm, this.sortFieldDescriptor);
            Stack<E> stack = map.get(fieldValue);
            if (stack != null)
                return stack.remove(dm); // >>> disgrace of O(n) performance
            else
                return false;
        }catch (TException e){
            e.log();
            return false;
        }
    }


    public void clear() {
        map.clear();
        this.size= 0;
        modCount++;
    }


    public  boolean addAll(Collection<? extends E> c) {
        // Use linear-time version if applicable
        /*if (map.size()==0 && c.size() > 0 &&
                c instanceof SortedSet &&
                map instanceof TreeMap) {
            SortedSet<? extends E> set = (SortedSet<? extends E>) c;
            TreeMap<E,Object> map = (TreeMap<E, Object>) this.map;
            Comparator<?> cc = set.comparator();
            Comparator<? super E> mc = map.comparator();
            if (cc==mc || (cc != null && cc.equals(mc))) {
                map.addAllForTreeSet(set, PRESENT);
                return true;
            }
        }*/
        return super.addAll(c);
    }


    // >>> SortedSet apis implementation
    //------------------------------------------------------------------------------------------------------------------
    public E first() {
        Stack<E> list= map.firstEntry().getValue();
        if (list == null)
            return null;
        return list.firstElement();
    }

    public E last() {
        Stack<E> list= map.lastEntry().getValue();
        if (list == null)
            return null;
        return list.lastElement();
    }

    public TSortedDataSet<E> headSet(E toElement, boolean inclusive) {
        Object toValue = TDataModelHelper.getFieldValueOrNull(toElement, this.sortFieldDescriptor);
        return this.headSetByFieldValue(toValue, inclusive);
    }

    public TSortedDataSet<E> headSet(E toElement) {
        Object toValue = TDataModelHelper.getFieldValueOrNull(toElement, this.sortFieldDescriptor);
        return this.headSetByFieldValue(toValue);
    }

    public TSortedDataSet<E> subSet(E fromElement, boolean fromInclusive,
                                  E toElement,   boolean toInclusive) {
        Object fromValue = TDataModelHelper.getFieldValueOrNull(fromElement, this.sortFieldDescriptor);
        Object toValue= TDataModelHelper.getFieldValueOrNull(toElement, this.sortFieldDescriptor);
        return this.subSetByFieldValue(fromValue, fromInclusive, toValue, toInclusive);
    }

    public TSortedDataSet<E> subSet(E fromElement, E toElement) {
        Object fromValue = TDataModelHelper.getFieldValueOrNull(fromElement, this.sortFieldDescriptor);
        Object toValue= TDataModelHelper.getFieldValueOrNull(toElement, this.sortFieldDescriptor);
        return this.subSetByFieldValue(fromValue, toValue);
    }

    public TSortedDataSet<E> tailSet(E fromElement, boolean inclusive) {
        Object fromValue = TDataModelHelper.getFieldValueOrNull(fromElement, this.sortFieldDescriptor);
        return this.tailSetByFieldValue(fromValue, inclusive);
    }


    public TSortedDataSet<E> tailSet(E fromElement) {
        Object fromValue = TDataModelHelper.getFieldValueOrNull(fromElement, this.sortFieldDescriptor);
        return this.tailSetByFieldValue(fromValue);
    }


    // >>> TSortedDataSet apis implementation
    //------------------------------------------------------------------------------------------------------------------

    @Override
    public TFieldDescriptor getSortField() {
        return this.sortFieldDescriptor;
    }


    @Override
    public TSortedDataSet<E> headSetByFieldValue(Object toFieldValue, boolean inclusive) {
        SortedMap<Object, Stack<E>> headMap = map.headMap(toFieldValue, inclusive);
        return new TDataTreeSet<>(this.sortFieldDescriptor, headMap);
    }

    @Override
    public TSortedDataSet<E> headSetByFieldValue(Object toFieldValue) {
        SortedMap<Object, Stack<E>> headMap = map.headMap(toFieldValue);
        return new TDataTreeSet<>(this.sortFieldDescriptor, headMap);
    }

    @Override
    public TSortedDataSet<E> subSetByFieldValue(Object fromFieldValue, boolean fromInclusive, Object toFieldValue, boolean toInclusive) {
        SortedMap<Object, Stack<E>> headMap = map.subMap(fromFieldValue, fromInclusive, toFieldValue, toInclusive);
        return new TDataTreeSet<>(this.sortFieldDescriptor, headMap);
    }

    @Override
    public TSortedDataSet<E> subSetByFieldValue(Object fromFieldValue, Object toFieldValue) {
        SortedMap<Object, Stack<E>> headMap = map.subMap(fromFieldValue, toFieldValue);
        return new TDataTreeSet<>(this.sortFieldDescriptor, headMap);
    }

    @Override
    public TSortedDataSet<E> subSetByFieldValue(Object fieldValue) {
        Stack<E> stack = map.get(fieldValue);
        return new TDataTreeSet<>(this.sortFieldDescriptor, stack);
    }

    @Override
    public TSortedDataSet<E> tailSetByFieldValue(Object fromFieldValue, boolean inclusive) {
        SortedMap<Object, Stack<E>> headMap = map.tailMap(fromFieldValue, inclusive);
        return new TDataTreeSet<>(this.sortFieldDescriptor, headMap);
    }

    @Override
    public TSortedDataSet<E> tailSetByFieldValue(Object fromFieldValue) {
        SortedMap<Object, Stack<E>> headMap = map.tailMap(fromFieldValue);
        return new TDataTreeSet<>(this.sortFieldDescriptor, headMap);
    }

    @Override
    public TDataSet<E> toDataSet() {
        return new TDataSet(this);
    }

    public Comparator<? super E> comparator() {
        return map.comparator();
    }



    // >>> NavigableSet API methods ignored :/


    @SuppressWarnings("unchecked")
    public TDataTreeSet<E> clone() {
        TDataTreeSet<E> newClone;
        try {
            newClone = (TDataTreeSet<E>) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
        refreshTheClone(newClone);
        return newClone;
    }

    private void refreshTheClone(TDataTreeSet<E> newClone){

        newClone.map = new TreeMap<>(map);
        // >>> Cloning lists on map values is obviously needed !
        newClone.map.replaceAll((fieldValue, dmList) -> {
            Stack<E> newList= new Stack<>();
            newList.addAll(dmList);
            newClone.size+= newList.size();
            return newList;
        });

        // >>> Put clone into "virgin" state
        newClone.modCount = 0;
    }


    /*
    private void writeObject(java.io.ObjectOutputStream s)
            throws java.io.IOException {
        // Write out any hidden stuff
        s.defaultWriteObject();

        // Write out Comparator
        s.writeObject(map.comparator());

        // Write out size
        s.writeInt(map.size());

        // Write out all elements in the proper order.
        for (E e : map.keySet())
            s.writeObject(e);
    }

    private void readObject(java.io.ObjectInputStream s)
            throws java.io.IOException, ClassNotFoundException {
        // Read in any hidden stuff
        s.defaultReadObject();

        // Read in Comparator
        @SuppressWarnings("unchecked")
        Comparator<? super E> c = (Comparator<? super E>) s.readObject();

        // Create backing TreeMap
        TreeMap<E,Object> tm = new TreeMap<>(c);
        map = tm;

        // Read in size
        int size = s.readInt();

        tm.readTreeSet(size, s, PRESENT);
    }
    */


    public Spliterator<E> spliterator() {

        return new TDataTreeSetSpliterator(this.map.entrySet().spliterator());
    }






    // >>> DataTreeSet Iterator
    //------------------------------------------------------------------------------------------------------------------
    /**
     * DataTreeSet Iterator
     */
    public class TDataTreeSetIterator<E> implements Iterator<E> {
        //Map.Entry<Object, LinkedList<E>> next;
        //Map.Entry<Object, LinkedList<E>> lastReturned;
        E next;
        E lastReturned;
        int expectedModCount; // for concurrent modification exception (CME) checks

        Iterator<Map.Entry<Object, List<E>>> mapIterator;
        Iterator<E> listIterator;



        TDataTreeSetIterator(Set<Map.Entry<Object, List<E>>> set) {

            mapIterator= set.iterator();
            lastReturned = null;

            this.prepareNext();

            this.expectedModCount = modCount;
        }

        private void prepareNext(){

            while (true){
                if ((listIterator == null) || ( ! listIterator.hasNext()) ){
                    if ( ! mapIterator.hasNext()){
                        this.next= null;
                        return;
                    } else {
                        listIterator= mapIterator.next().getValue().iterator();
                    }
                }
                this.next= this.listIterator.next();
                return;

            }
        }

        public final boolean hasNext() {
            return next != null;
        }

        @Override
        public E next() {

            E e = this.next;
            if (e == null)
                throw new NoSuchElementException();
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
            this.prepareNext();
            lastReturned = e;
            return e;
        }

    }


    // >>> DataTreeSet Spliterator
    //------------------------------------------------------------------------------------------------------------------
    // Implement minimal Spliterator as KeySpliterator backup
    final class TDataTreeSetSpliterator<E> implements Spliterator<E> {

        E currentElement;
        int expectedModCount; // for concurrent modification exception (CME) checks

        protected Spliterator<Map.Entry<Object, List<E>>> mapSpliterator;
        protected Spliterator<E> listSpliterator;


        public TDataTreeSetSpliterator( Spliterator<Map.Entry<Object, List<E>>> mapSpliterator) {

            this.mapSpliterator= mapSpliterator;
            this.expectedModCount= modCount;
        }

        private E prepareNext(){

            currentElement= null;
            boolean noMoreList= true;
            while (true){
                if (listSpliterator != null)
                    listSpliterator.tryAdvance(e -> this.currentElement= e);

                if (this.currentElement != null)
                    return this.currentElement;
                else
                    listSpliterator= null; // >>> No more thing to do with that list

                if (mapSpliterator != null)
                    noMoreList= ! this.mapSpliterator.tryAdvance(objectListEntry
                            -> this.listSpliterator= objectListEntry.getValue().spliterator());

                if (noMoreList || listSpliterator == null)
                    return null;
            }
        }

        @Override
        public boolean tryAdvance(Consumer<? super E> action) {

            E nextElement= this.prepareNext();
            if (nextElement == null)
                return false;
            action.accept(nextElement);

            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();

            return true;
        }

        @Override
        public Spliterator<E> trySplit() {
            Spliterator<Map.Entry<Object, List<E>>> newMapSpliterator= this.mapSpliterator.trySplit();

            TDataTreeSetSpliterator<E> newSpliterator= new TDataTreeSetSpliterator(newMapSpliterator);

            if (newSpliterator.mapSpliterator == null) {
                newSpliterator.listSpliterator = this.listSpliterator.trySplit();
                if (newSpliterator.listSpliterator == null)
                    return null; // >>> No thing remained to split
            }
            return newSpliterator;
        }

        @Override
        public long estimateSize() {
            return 0;
        }

        @Override
        public int characteristics() {
            return Spliterator.SORTED | Spliterator.ORDERED;
        }
    }

}

