public class Main {

	public static void testOne()
	{
		char[] letters = {'E', 'Q', 'R', 'T', 'W', 'Y'};
		Trie trie = new Trie(letters);
			
		trie.insert("TERRY");
		trie.insert("WERE");
		trie.insert("RYE");
		trie.insert("TYRE");
		trie.insert("YET");
		trie.insert("EYE");
		trie.print(true);
		System.out.println();

		trie.insert("YEW");
		trie.insert("WRY");
		trie.insert("ERR");
		trie.insert("EWE");
		trie.insert("EWER");
		trie.print(false);

		System.out.println(trie.contains("WHERE"));
		System.out.println(trie.contains("TERRY"));
		System.out.println(trie.contains("JEW"));
		System.out.println(trie.contains("EWE"));

		trie.printKeyList();		
	}
	public static void testTwo()
	{
		char[] letters ={'s','i','m','l','a','r'};
		String[] words={"amirs",
		"arils",
		"lairs",
		"laris",
		"liars",
		"limas",
		"liras",
		"mails",
		"mairs",
		"marls",
		"milia",
		"rails",
		"rials",
		"salmi",
		"simar",
		"ails",
		"aims",
		"airs",
		"alms",
		"amir",
		"amis",
		"aril",
		"arms",
		"ilia",
		"iris",
		"lair",
		"lams",
		"lari",
		"lars",
		"liar",
		"lima",
		"lira",
		"liri",
		"mail",
		"mair",
		"marl",
		"mars",
		"mils",
		"miri",
		"mirs",
		"rail",
		"rami",
		"rams",
		"rial",
		"rias",
		"rims",
		"sail",
		"sari",
		"sial",
		"sima",
		"slam",
		"slim",
		"ail",
		"aim",
		"air",
		"ais",
		"als",
		"ami",
		"arm",
		"ars",
		"ism",
		"lam",
		"lar",
		"las",
		"lis",
		"mar",
		"mas",
		"mil",
		"mir",
		"mis",
		"ram",
		"ras",
		"ria",
		"rim",
		"sal",
		"sim",
		"sir",
		"sri",
		"ai",
		"al",
		"am",
		"ar",
		"as",
		"is",
		"la",
		"li",
		"ma",
		"mi",
		"si"
	};
		Trie trie=new Trie(letters);
		for (int i=words.length-1;i>=0;i--)
		{
			trie.insert(words[i]);
			System.out.println("INSERTED "+words[i]);
			System.out.println("-----------------");
			trie.print(true);
		}
	}

	public static void main(String[] args)
	{
		testOne();
		testTwo();

		/* Expected Output:
		Level 1 [ (#,0)  (E,1)  (Q,0)  (R,1)  (T,1)  (W,1)  (Y,1) ]
		Level 2 [EYE]
		Level 2 [RYE]
		Level 2 [ (#,0)  (E,1)  (Q,0)  (R,0)  (T,0)  (W,0)  (Y,1) ]
		Level 2 [WERE]
		Level 2 [YET]
		Level 3 [TERRY]
		Level 3 [TYRE]

		Level 1 [ (E,1)  (R,1)  (T,1)  (W,1)  (Y,1) ]
		Level 2 [ (R,1)  (W,1)  (Y,1) ]
		Level 2 [RYE]
		Level 2 [ (E,1)  (Y,1) ]
		Level 2 [ (E,1)  (R,1) ]
		Level 2 [ (E,1) ]
		Level 3 [ERR]
		Level 3 [ (E,1) ]
		Level 3 [EYE]
		Level 3 [TERRY]
		Level 3 [TYRE]
		Level 3 [WERE]
		Level 3 [WRY]
		Level 3 [ (T,1)  (W,1) ]
		Level 4 [ (#,1)  (R,1) ]
		Level 4 [YET]
		Level 4 [YEW]
		Level 5 [EWE]
		Level 5 [EWER]
		false
		true
		false
		true
		ERR EWE EWER EYE RYE TERRY TYRE WERE WRY YET YEW
		*/
	}
}
