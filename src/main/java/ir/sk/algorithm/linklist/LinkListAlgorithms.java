package ir.sk.algorithm.linklist;

import ir.sk.adt.datastructure.linklist.DoubledLink;
import ir.sk.adt.datastructure.linklist.SinglyLink;
import ir.sk.helper.Difficulty;
import ir.sk.helper.DifficultyType;
import ir.sk.helper.Point;
import ir.sk.helper.RecurrenceRelation;
import ir.sk.helper.complexity.InPlace;
import ir.sk.helper.complexity.SpaceComplexity;
import ir.sk.helper.complexity.TimeComplexity;
import ir.sk.helper.paradigm.BruteForce;
import ir.sk.helper.pattern.HashingIndexPattern;
import ir.sk.helper.pattern.TwoPointerPattern;
import ir.sk.helper.pattern.RunnerPattern;
import ir.sk.helper.recursiontype.HeadRecursion;

import java.util.*;

/**
 * Created by sad.kayvanfar on 9/1/2020.
 */
public class LinkListAlgorithms {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * delete duplicates in a link list
     *
     * iterate with two pointers: current which iterates through the linked list,
     * and runner which checks all subsequent nodes for duplicates.
     * <p>
     * Using Runner Technique
     *
     * @param head
     */
    @TimeComplexity("O(n^2)")
    @SpaceComplexity("O(1)")
    @Point("Using two Loop")
    @BruteForce
    public static void deleteDuplicatesByRunner(SinglyLink head) {
        SinglyLink current = head;
        while (current != null) {
            SinglyLink runner = head;
            while (runner.next != null) {
                if (current.data.equals(runner.next.data)) {
                    runner.next = runner.next.next;
                } else
                    runner = runner.next;
            }
            current = current.next;
        }
    }

    /**
     * iterate through the linked list, adding each element to a hash table. When
     * we discover a duplicate element, we remove the element and continue iterating.
     *
     * @param head
     */
    @TimeComplexity("O(n)")
    @SpaceComplexity("O(n)")
    @HashingIndexPattern
    public static void deleteDuplicatesByHashing(SinglyLink<Integer> head) {
        HashSet<Integer> hashTable = new HashSet<>();
        SinglyLink<Integer> previous = null;

        while (head != null) {
            if (hashTable.contains(head.data))
                previous.next = head.next;
            else {
                hashTable.add(head.data);
                previous = head;
            }
            head = head.next;
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Find the nth node from the end in the given linked list
     *
     * @param head
     * @param k
     * @return
     */
    public static <T> SinglyLink<T> kthToLastRecursive(SinglyLink<T> head, int k) {
        Index idx = new Index();
        return kthToLastRecursive(head, k, idx);
    }

    /**
     * wrap the counter value with simple class
     */
    @Point("Wrapper of primitive type in order to pass as parameter in methods")
    static class Index {
        public int value = 0;
    }

    /**
     * This algorithm recurses through the linked list. When it hits the end, the method passes back a counter set
     * to 0. Each parent call adds 1 to this counter. When the counter equals k, we know we have reached the kth
     * to last element of the linked list.
     *
     * @param head
     * @param k
     * @param idx
     * @return
     */
    @Difficulty(type = DifficultyType.HARD)
    @TimeComplexity("O(n)")
    @SpaceComplexity("O(n)")
    private static <T> SinglyLink<T> kthToLastRecursive(SinglyLink<T> head, int k, Index idx) {
        if (head == null)
            return null;

        SinglyLink<T> node = kthToLastRecursive(head.next, k, idx);
        idx.value = idx.value + 1;
        if (idx.value == k)
            return head;

        return node;
    }

    /**
     * use two pointers,
     * pl and p2. We place them k nodes apart in the linked list by putting p2 at the beginning and moving pl
     * k nodes into the list. Then, when we move them at the same pace, pl will hit the end of the linked list after
     * LENGTH - k steps. At that point, p2 will be LENGTH - k nodes into the list, or k nodes from the end.
     *
     * @param head
     * @param k
     * @return
     */
    @TimeComplexity("O(n)")
    @SpaceComplexity("O(1)")
    @RunnerPattern
    @Difficulty(type = DifficultyType.HARD)
    private static <T> SinglyLink<T> nthToLastByRunner(SinglyLink<T> head, int k) {
        SinglyLink<T> current = head;
        SinglyLink<T> runner = head;

        /* Move pl k nodes into the list.*/
        for (int i = 0; i < k; i++) {
            if (runner == null)
                return null; // Out of bounds
            runner = runner.next;
        }

        /* Move them at the same pace. When pl hits the end, runner will be at the right 12 * element. */
        while (runner != null) {
            runner = runner.next;
            current = current.next;
        }
        return current;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * delete a node in the middle (i.e., any node but
     * the first and last node, not necessarily the exact middle) of a singly linked list, given only access to
     * that node.
     * <p>
     * You only have access to that node.
     * The solution is simply to copy the data from the next node over to the current node, and then to delete the
     * next node.
     *
     * @param n
     * @return
     */
    private static <T> boolean deleteNode(SinglyLink<T> n) {
        if (n == null || n.next == null)
            return false; // Failure

        SinglyLink<T> next = n.next;
        n.data = next.data;
        n.next = next.next;
        return true;
    }

    /**
     * You have two numbers represented by a linked list, where each node contains a single
     * digit. The digits are stored in reverse order, such that the 1 's digit is at the head of the list. this
     * function adds the two numbers and returns the sum as a linked list.
     */
    @TimeComplexity("O(m+n)")
    @SpaceComplexity("O(m+n)")
    public static SinglyLink<Integer> sumRevertedLists(SinglyLink<Integer> list1, SinglyLink<Integer> list2, int carry) {
        if (list1 == null && list2 == null && carry == 0)
            return null;

        SinglyLink<Integer> result = new SinglyLink<>();

        int value = carry;
        if (list1 != null)
            value += list1.data;

        if (list2 != null)
            value += list1.data;

        result.data = value % 10; /* Second digit of number */

        if (list1 != null || list2 != null) {
            SinglyLink<Integer> more = sumRevertedLists(list1 == null ? null : list1.next,
                    list2 == null ? null : list2.next,
                    value >= 10 ? 1 : 0);
            result.next = more;
        }
        return result;
    }

    /**
     * Given the head of a Singly LinkedList, write a method to modify the LinkedList such that the nodes
     * from the second half of the LinkedList are inserted alternately to the nodes from the first half in reverse order.
     * So if the LinkedList has nodes 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> null, your method should return 1 -> 6 -> 2 -> 5 -> 3 -> 4 -> null.
     * <p>
     * This problem shares similarities with Palindrome LinkedList. To rearrange the given LinkedList we will follow the following steps:
     * <p>
     * We can use the Fast & Slow pointers method similar to Middle of the LinkedList to find the middle node of the LinkedList.
     * Once we have the middle of the LinkedList, we will reverse the second half of the LinkedList.
     * Finally, we’ll iterate through the first half and the reversed second half to produce a LinkedList in the required order.
     *
     * @param head
     */
    @TimeComplexity("O(n)")
    @SpaceComplexity("O(1)")
    @RunnerPattern
    public static <T> void reOrder(SinglyLink<T> head) {
        SinglyLink<T> middleLink = findMiddleLink(head);
        SinglyLink<T> reversed = reverseIterative(middleLink);
        while (reversed != null) {
            SinglyLink<T> tmp = head.next;
            head.next = reversed;
            head = tmp;

            tmp = reversed.next;
            reversed.next = head;
            reversed = tmp;
        }
        if (head != null)
            head.next = null;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Given pointer to the head node of a linked list, the task is to reverse the linked list. We need to reverse the list by changing the links between nodes.
     * 1->2->3->4->null
     * 4->3->2->1->null
     *
     * @param node
     * @return
     */
    @TimeComplexity("O(n)")
    @SpaceComplexity("O(1)")
    @InPlace
    public static <T> SinglyLink<T> reverseIterative(SinglyLink<T> node) {
        SinglyLink<T> prev = null;
        SinglyLink<T> current = node;
        SinglyLink<T> next = null;
        while (current != null) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        node = prev;
        return node;
    }

    /**
     * 1->2->3->4->null
     * 4->3->2->1->null
     *
     * @param first
     * @return
     */
    @RecurrenceRelation("T(n) = T(n-1) + O(1)")
    @TimeComplexity("O(n)")
    @SpaceComplexity("O(1)")
    @HeadRecursion
    public static <T> SinglyLink<T> reverseRecursive(SinglyLink<T> first) {
        if (first == null) return null;
        if (first.next == null) return first;
        SinglyLink<T> second = first.next;
        SinglyLink<T> rest = reverseRecursive(second);
        second.next = first;
        first.next = null;
        return rest;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * @param node
     * @return
     */
    public static <T> SinglyLink<T> reverseAndClone(SinglyLink<T> node) {
        SinglyLink<T> head = null;
        while (node != null) {
            SinglyLink<T> n = new SinglyLink<>(node.data); // Clone
            n.next = head;
            head = n;
            node = node.next;
        }
        return head;
    }

    private static boolean isEqual(SinglyLink<Integer> one, SinglyLink<Integer> two) {
        while (one != null && two != null) {
            if (one.data != two.data)
                return false;

            one = one.next;
            two = two.next;
        }
        return one == null && two == null;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * is a linklist palindrome
     *
     * 1) Get the middle of the linked list.
     * 2) Reverse the second half of the linked list.
     * 3) Check if the first half and second half are identical.
     *
     * @param head
     * @return
     */
    @TimeComplexity("O(n)")
    @SpaceComplexity("O(1)")
    @RunnerPattern
    public static boolean isPalindromeByRunner(SinglyLink<Integer> head) {
        SinglyLink<Integer> middleLink = findMiddleLink(head);
        SinglyLink<Integer> reversed = reverseIterative(middleLink);
        return isEqual(head, reversed);
    }


    /**
     * We need to push the first half of the elements onto a stack
     * If we don't know the size of the linked list, we can iterate through the linked list, using the fast runner/ slow
     * runner technique
     * At each step in the loop, we push the data from
     * the slow runner onto a stack. When the fast runner hits the end of the list, the slow runner will have reached
     * the middle of the linked list. By this point, the stack will have all the elements from the front of the linked
     * list, but in reverse order.
     *
     * @param head
     * @return
     */
    @TwoPointerPattern
    @TimeComplexity("O(n/2) = O(n)")
    @SpaceComplexity("O(n/2) = O(n)")
    public static boolean isPalindromeByStack(SinglyLink<Integer> head) {
        SinglyLink<Integer> fast = head;
        SinglyLink<Integer> slow = head;

        Stack<Integer> stack = new Stack<>();

        /* Push elements from first half of linked list onto stack. When fast runner
         * (which is moving at 2x speed) reaches the end of the linked list, then we
         * know we're at the middle*/
        while (fast != null && fast.next != null) {
            stack.push(slow.data);
            slow = slow.next;
            fast = fast.next.next;
        }

        /* Has odd number of elements, so skip the middle element*/
        if (fast != null)
            slow = slow.next;

        while (slow != null) {
            int top = stack.pop().intValue();

            /* If values are different, then it's not a palindrome*/
            if (top != slow.data)
                return false;

            slow = slow.next;
        }
        return true;
    }

    static class Result<T> {
        public SinglyLink<T> node;
        public boolean result;

        public Result(SinglyLink<T> node, boolean result) {
            this.node = node;
            this.result = result;
        }
    }

    /**
     * we first need to know when we've reached the middle element, as this will
     * form our base case. We can do this by passing in length - 2 for the length each time. When the length
     * equals 0 or 1, we're at the center of the linked list. This is because the length is reduced by 2 each time. Once
     * we've recursed Yi times, length will be down to 0
     *
     * @param head
     * @return
     */
    public static <T> boolean isPalindromeByRecurse(SinglyLink<T> head) {
        int length = lengthOfList(head);
        Result p = isPalindromeRecurse(head, length);
        return p.result;
    }

    private static <T> Result isPalindromeRecurse(SinglyLink<T> head, int length) {
        if (head == null || length <= 0) {
            // Even number of nodes
            return new Result(head, true);
        } else if (length == 1) {
            // Odd number of nodes
            return new Result(head.next, true);
        }

        // Recurse on sublist.
        Result res = isPalindromeRecurse(head.next, length - 2);

        // If child calls are not a palindrome, pass back up a failure.
        if (!res.result || res.node == null) {
            return res;
        }

        // Check if matches corresponding node on other side.
        res.result = (head.data == res.node.data);

        // Return corresponding node.
        res.node = res.node.next;

        return res;
    }

    /**
     * You are given a doubly linked list. Determine if it is a palindrome.
     * <p>
     * Initialize two pointers left at starting of list and right at the end of the list.
     * Check the data at left node is equal to right node, if it is equal then increment left and decrement right till middle of the list,
     * if at any stage it is not equal then return false.
     *
     * @param left
     * @return
     */
    @TimeComplexity("O(n)")
    @SpaceComplexity("O(1)")
    @Difficulty(type = DifficultyType.EASY)
    @TwoPointerPattern
    public static <T> boolean isPalindrome(DoubledLink<T> left) {
        if (left == null)
            return true;

        // find the rightmost node
        DoubledLink<T> right = left;
        while (right.next != null)
            right = right.next;

        while (left != right) {
            if (left.data != right.data)
                return false;

            left = left.next;
            right = right.previous;
        }
        return true;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static <T> int lengthOfList(SinglyLink<T> n) {
        int size = 0;
        while (n != null) {
            size++;
            n = n.next;
        }
        return size;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Given the head of a Singly LinkedList, write a function to determine if the LinkedList has a cycle in it or not.
     * Traverse the list one by one and keep putting the node addresses in a Hash Table. At any point,
     * if NULL is reached then return false and if next of current node points to any of the previously
     * stored nodes in Hash then return true
     * <p>
     * 1->2->3->4->5->6->3
     *
     * @param head
     * @return
     */
    @TimeComplexity("O(n)")
    @SpaceComplexity("O(n)")
    @HashingIndexPattern
    public static <T> boolean hasCycleByHashing(SinglyLink<T> head) {
        SinglyLink<T> current = head;
        Set<SinglyLink<T>> hashtable = new HashSet<>();
        while (current != null) {
            if (hashtable.contains(current))
                return true;
            else {
                hashtable.add(current);
                current = current.next;
            }
        }
        return false;
    }

    /**
     * Given the head of a Singly LinkedList, write a function to determine if the LinkedList has a cycle in it or not.
     * <p>
     * Imagine two racers running in a circular racing track. If one racer is faster than the other, the faster racer is bound to catch up and cross the slower racer from behind. We can use this fact to devise an algorithm to determine if a LinkedList has a cycle in it or not.
     * <p>
     * Imagine we have a slow and a fast pointer to traverse the LinkedList. In each iteration, the slow pointer moves one step and the fast pointer moves two steps. This gives us two conclusions:
     * <p>
     * If the LinkedList doesn’t have a cycle in it, the fast pointer will reach the end of the LinkedList before the slow pointer to reveal that there is no cycle in the LinkedList.
     * The slow pointer will never be able to catch up to the fast pointer if there is no cycle in the LinkedList.
     * If the LinkedList has a cycle, the fast pointer enters the cycle first, followed by the slow pointer. After this, both pointers will keep moving in the cycle infinitely. If at any stage both of these pointers meet, we can conclude that the LinkedList has a cycle in it. Let’s analyze if it is possible for the two pointers to meet. When the fast pointer is approaching the slow pointer from behind we have two possibilities:
     * <p>
     * The fast pointer is one step behind the slow pointer.
     * The fast pointer is two steps behind the slow pointer.
     * All other distances between the fast and slow pointers will reduce to one of these two possibilities. Let’s analyze these scenarios, considering the fast pointer always moves first:
     * <p>
     * If the fast pointer is one step behind the slow pointer: The fast pointer moves two steps and the slow pointer moves one step, and they both meet.
     * If the fast pointer is two steps behind the slow pointer: The fast pointer moves two steps and the slow pointer moves one step. After the moves, the fast pointer will be one step behind the slow pointer, which reduces this scenario to the first scenario. This means that the two pointers will meet in the next iteration.
     * This concludes that the two pointers will definitely meet if the LinkedList has a cycle.
     * <p>
     * 1->2->3->4->5->6->3
     * <p>
     * floyds way
     *
     * @param head
     * @return
     */
    @TimeComplexity("O(n)")
    @SpaceComplexity("O(1)")
    @RunnerPattern
    public static <T> boolean hasCycleByRunner(SinglyLink<T> head) {
        SinglyLink<T> slow = head, fast = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (slow == fast)
                return true;
        }
        return false;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Given the head of a LinkedList with a cycle, find the length of the cycle.
     * <p>
     * Once the fast and slow pointers meet, we can save the slow pointer and iterate the whole cycle with another pointer until we see the slow pointer again to find the length of the cycle.
     *
     * @param head
     * @return
     */
    @TimeComplexity("O(n)")
    @SpaceComplexity("O(1)")
    @RunnerPattern
    public static <T> int findCycleLength(SinglyLink<T> head) {
        SinglyLink<T> slow = head;
        SinglyLink<T> fast = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (slow == fast)
                return calculateLength(slow);
        }
        return 0;
    }

    private static <T> int calculateLength(SinglyLink<T> slow) {
        SinglyLink<T> current = slow;
        int cycleLength = 0;
        do {
            current = current.next;
            cycleLength++;
        } while (current != slow);
        return cycleLength;
    }

    /**
     * If we know the length of the LinkedList cycle, we can find the start of the cycle through the following steps:
     * <p>
     * Take two pointers. Let’s call them pointer1 and pointer2.
     * Initialize both pointers to point to the start of the LinkedList.
     * We can find the length of the LinkedList cycle using the approach discussed in LinkedList Cycle. Let’s assume that the length of the cycle is ‘K’ nodes.
     * Move pointer2 ahead by ‘K’ nodes.
     * Now, keep incrementing pointer1 and pointer2 until they both meet.
     * As pointer2 is ‘K’ nodes ahead of pointer1, which means, pointer2 must have completed one loop in the cycle when both pointers meet. Their meeting point will be the start of the cycle.
     *
     * @param head
     * @return
     */
    @TimeComplexity("O(n)")
    @SpaceComplexity("O(1)")
    @RunnerPattern
    public static <T> SinglyLink<T> findCycleStart(SinglyLink<T> head) {
        int cycleLength = findCycleLength(head);
        return findStart(head, cycleLength);
    }

    private static <T> SinglyLink<T> findStart(SinglyLink<T> head, int cycleLength) {
        SinglyLink<T> pointer1 = head, pointer2 = head;

        // move pointer1 ahead 'cycLength' nodes
        while (cycleLength > 0) {
            pointer2 = pointer2.next;
            cycleLength--;
        }

        // increment both pointers until they meet at the start of the cycle
        while (pointer1 != pointer2) {
            pointer1 = pointer1.next;
            pointer2 = pointer2.next;
        }

        return pointer1;
    }

    /**
     * Given the head of a Singly LinkedList, write a method to return the middle node of the LinkedList.
     * <p>
     * One brute force strategy could be to first count the number of nodes in the LinkedList and then find the middle node in the second iteration.
     * <p>
     * We can use the Fast & Slow pointers method such that the fast pointer is always twice the nodes ahead of the slow pointer.
     * This way, when the fast pointer reaches the end of the LinkedList,
     * the slow pointer will be pointing at the middle node.
     *
     * @param head
     * @return
     */
    @TimeComplexity("O(n)")
    @SpaceComplexity("O(1)")
    @RunnerPattern
    public static <T> SinglyLink<T> findMiddleLink(SinglyLink<T> head) {
        SinglyLink<T> slow = head;
        SinglyLink<T> fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    /**
     * Delete continuous nodes with sum K from a given linked list
     *
     * Input: Linked List: 1 -> 2 -> -3 -> 3 -> 1, K = 3
     *
     * Output: -3 -> 1
     * @param head
     * @param k
     * @return
     */
    @TimeComplexity("O(n)")
    @SpaceComplexity("O(n)")
    @Difficulty(type = DifficultyType.HARD)
    public static SinglyLink<Integer> removeZeroSum(SinglyLink<Integer> head, int k) {
        // Root node initialise to 0
        SinglyLink<Integer> root = new SinglyLink<>(0);

        // Append at the front of the given
        // Linked List
        root.next = head;

        // Map to store the sum and reference
        // of the Node
        @HashingIndexPattern
        Map<Integer, SinglyLink<Integer>> umap = new HashMap<>();

        umap.put(0, root);

        // To store the sum while traversing
        int sum = 0;

        // Traversing the Linked List
        while (head != null) {

            // Find sum
            sum += head.data;

            // If found value with (sum - K)
            if (umap.containsKey(sum - k)) {

                SinglyLink<Integer> prev = umap.get(sum - k);
                SinglyLink<Integer> start = prev;

                // Delete all the node
                // traverse till current node
                int aux = sum;

                // Update sum
                sum = sum - k;

                // Traverse till current head
                while (prev != head) {
                    prev = prev.next;
                    aux += prev.data;
                    if (prev != head) {
                        umap.remove(aux);
                    }
                }

                // Update the start value to
                // the next value of current head
                start.next = head.next;
            }

            // If (sum - K) value not found
            else if (!umap.containsKey(sum)) {
                umap.put(sum, head);
            }

            head = head.next;
        }

        // Return the value of updated
        // head node
        return root.next;
    }

    /**
     * You are given a singly linked list and an integer k. Return the linked list, removing the k-th last element from the list.
     *
     * @param head
     * @param k
     * @param <T>
     * @return
     */
    @TimeComplexity("O(n)")
    @RunnerPattern
    @Difficulty(type = DifficultyType.MEDIUM)
    public static <T> void removeKthFromLinkedList(SinglyLink<T> head, int k) {
        SinglyLink<T> slowLink = head, fastLink = head;
        for (int i = 0; i < k; i++) {
            // If count of nodes in the given
            // linked list is <= N
            if (fastLink.next == null) {
                // If count = N i.e. delete the head node
                if (i == k - 1)
                    head = head.next;
                return;
            }
            fastLink = fastLink.next;
        }

        while (fastLink != null) {
            fastLink = fastLink.next;
            slowLink = slowLink.next;
        }

        slowLink.next = slowLink.next.next;
    }
}
