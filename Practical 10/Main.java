// Name: Xander Bezuidenhout
// Student number: 20425997

public class Main {

    public static void main(String[] args) {

        DynamicHashMap map = new DynamicHashMap(3, 3.0);
        System.out.println(map.toString());

        System.out.println("h(I) = " + map.hash("I"));
        map.put("I", 1);

	System.out.println("h(LOVE) = " + map.hash("LOVE"));
        map.put("LOVE", 4);

        System.out.println("h(COS) = " + map.hash("COS"));
        map.put("COS", 216);
        System.out.println(map.toString());
        map.put("COS", 212);
        System.out.println(map.toString());

        System.out.println("h(KEY) = " + map.hash("KEY"));
        map.put("KEY", 50);
        System.out.println(map.toString());

        System.out.println("h(TREE) = " + map.hash("TREE"));
        map.put("TREE", 63);
        System.out.println(map.toString());

        map.put("A", 1);
        map.put("B", 2);
        map.put("C", 3);
        map.put("D", 4);
	System.out.println(map.toString());

        map.put("E", 5);
        System.out.println(map.toString());

        System.out.println("h(COS) = " + map.hash("COS"));
	System.out.println(map.remove("COS"));
	System.out.println("h(LOVE) = " + map.hash("LOVE"));
	System.out.println(map.remove("LOVE"));

        System.out.println(map.toString());

	System.out.println(map.get("KEY"));
	System.out.println(map.get("TREE"));

        /* Expected output:
        [][][]
	h(I) = 1
	h(LOVE) = 1
	h(COS) = 2
	[][1,4][216]
	[][1,4][212]
	h(KEY) = 0
	[50][1,4][212]
	h(TREE) = 0
	[50,63][1,4][212]
	[50,63,2][1,4,3][212,1,4]
	[63,2][1,3][4][50,5][4][212,1]
	h(COS) = 5
	212
	h(LOVE) = 4
	4
	[63,2][1,3][4][50,5][][1]
	50
	63
        */
    }

}