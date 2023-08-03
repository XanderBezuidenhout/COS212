public class Main {

	public static void createRecord(Table table, Object[] fields) {
		if (fields == null)
			return;
		Record r = new Record(fields.length);
		for (int i = 0; i < fields.length; i++) {
			r.setColumn(i + 1, fields[i]);
		}
		table.insert(r);
	}
	

	public static void main(String[] args) {
		 
		String[] columns = new String[] { "SongID", "Title", "Artist", "Genre", "Album" };
		Table musicCollection = new Table("Music", columns, 4); // indices will all be of order 4 in this example

		// Add some records
		createRecord(musicCollection, new Object[] { 124521, "Doing It Wrong", "Drake", "Hip Hop/Rap", "Take Care" });
		createRecord(musicCollection, new Object[] { 145821, "Shape of You", "Ed Sheeran", "Pop", "Divide" });
		createRecord(musicCollection, new Object[] { 914725, "Jerusalema", "Master KG", "Gospel-house", "Single" });
		createRecord(musicCollection, new Object[] { 965876, "On Your Mind", "Kaskade", "Dance", "Redux 003" });
		createRecord(musicCollection, new Object[] { 625751, "Memories", "David Guetta", "Dance", "One Love" });
		createRecord(musicCollection, new Object[] { 102254, "September", "Earth, Wind & Fire", "Disco", "September" });
		createRecord(musicCollection, new Object[] { 558521, "Boom Boom Pow", "The Black Eyed Peas", "Dance", "THE E.N.D" });
		createRecord(musicCollection, new Object[] { 656232, "Chaconne in G Minor", "Tomaso Antonio Vitali", "Classical", "Composition" });
		createRecord(musicCollection, new Object[] { 223578, "Fake Love", "Drake", "Hip Hop/Rap", "More Life" });
		createRecord(musicCollection, new Object[] { 226678, "Ladbroke Grove", "AJ Tracey", "Hip Hop/Rap", "AJ Tracey" });
		createRecord(musicCollection, new Object[] { 188547, "Never Gonna Give You Up", "Rick Astley", "Pop", "Whenever You Need Somebody" });
		createRecord(musicCollection, new Object[] { 915412, "The Lion From The North", "Sabaton", "Power Metal", "Carolus Rex" });
		createRecord(musicCollection, new Object[] { 325482, "Firework", "Katy Perry", "Pop", "Teenage Dream" });
		createRecord(musicCollection, new Object[] { 157456, "She Wolf", "David Guetta", "Dance", "Nothing but the Beat" });
		createRecord(musicCollection, new Object[] { 111125, "Afraid", "James Hype", "Dance", "Single" });
		createRecord(musicCollection, new Object[] { 333658, "In My Head", "Jason Derulo", "Pop", "Jason Derülo" });

		musicCollection.createIndex("ArtistIndex", "Artist");
		musicCollection.createIndex("GenreIndex", "Genre");
		
		System.out.println("-----------------Printing Artist Index-----------------");
		musicCollection.printIndex("ArtistIndex");
		
		System.out.println("-----------------Printing Genre Index-----------------");
		musicCollection.printIndex("GenreIndex");
		
		System.out.println("-----------------select * from Music-----------------");
		musicCollection.selectAll();
		
		System.out.println("\n-----------------select * from Music where Artist = 'David Guetta'-----------------");
		musicCollection.selectWhere("Artist", "David Guetta");
		
		System.out.println("\n-----------------delete from Music where Artist = 'Drake'-----------------");
		musicCollection.deleteWhere("Artist", "Drake");
		
		System.out.println("\n-----------------Printing Artist Index-----------------");
		musicCollection.printIndex("ArtistIndex");
		
		System.out.println("-----------------Printing Genre Index-----------------");
		musicCollection.printIndex("GenreIndex");
		
		System.out.println("\n-----------------select * from Music where Genre = 'Hip Hop/Rap'-----------------");
		musicCollection.selectWhere("Genre", "Hip Hop/Rap");
		
		System.out.println("\n-----------------select * from Music order by Artist-----------------");
		musicCollection.selectOrderBy("Artist");
		
		System.out.println("\n-----------------delete from Music where Genre = 'Pop'-----------------");
		musicCollection.deleteWhere("Genre", "Pop");
		
		System.out.println("\n-----------------select * from Music-----------------");
		musicCollection.selectAll();

		System.out.println("\n-----------------Printing Artist Index-----------------");
		musicCollection.printIndex("ArtistIndex");
		
		System.out.println("-----------------Printing Genre Index-----------------");
		musicCollection.printIndex("GenreIndex");
		
		System.out.println("\n-----------------delete from Music-----------------");
		musicCollection.deleteAll();
		
		System.out.println("\n-----------------select * from Music-----------------");
		musicCollection.selectAll();
		
		System.out.println("\n-----------------Printing Artist Index-----------------");
		musicCollection.printIndex("ArtistIndex");
		
		System.out.println("-----------------Printing Genre Index-----------------");
		musicCollection.printIndex("GenreIndex");
		
		/* Example Output
		-----------------Printing Artist Index-----------------
		Level 1 [Rick Astley]
		Level 2 [Earth, Wind & Fire | James Hype | Kaskade]
		Level 3 [AJ Tracey | David Guetta | Drake]
		Level 3 [Earth, Wind & Fire | Ed Sheeran]
		Level 3 [James Hype | Jason Derulo]
		Level 3 [Kaskade | Katy Perry | Master KG]
		Level 2 [The Black Eyed Peas]
		Level 3 [Rick Astley | Sabaton]
		Level 3 [The Black Eyed Peas | Tomaso Antonio Vitali]
		
		-----------------Printing Genre Index-----------------
		Level 1 [Disco | Hip Hop/Rap]
		Level 2 [Classical | Dance]
		Level 2 [Disco | Gospel-house]
		Level 2 [Hip Hop/Rap | Pop | Power Metal]
		
		-----------------select * from Music-----------------
		124521, Doing It Wrong, Drake, Hip Hop/Rap, Take Care
		145821, Shape of You, Ed Sheeran, Pop, Divide
		914725, Jerusalema, Master KG, Gospel-house, Single
		965876, On Your Mind, Kaskade, Dance, Redux 003
		625751, Memories, David Guetta, Dance, One Love
		102254, September, Earth, Wind & Fire, Disco, September
		558521, Boom Boom Pow, The Black Eyed Peas, Dance, THE E.N.D
		656232, Chaconne in G Minor, Tomaso Antonio Vitali, Classical, Composition
		223578, Fake Love, Drake, Hip Hop/Rap, More Life
		226678, Ladbroke Grove, AJ Tracey, Hip Hop/Rap, AJ Tracey
		188547, Never Gonna Give You Up, Rick Astley, Pop, Whenever You Need Somebody
		915412, The Lion From The North, Sabaton, Power Metal, Carolus Rex
		325482, Firework, Katy Perry, Pop, Teenage Dream
		157456, She Wolf, David Guetta, Dance, Nothing but the Beat
		111125, Afraid, James Hype, Dance, Single
		333658, In My Head, Jason Derulo, Pop, Jason Derülo
		
		-----------------select * from Music where Artist = 'David Guetta'-----------------
		157456, She Wolf, David Guetta, Dance, Nothing but the Beat
		625751, Memories, David Guetta, Dance, One Love
		
		-----------------delete from Music where Artist = 'Drake'-----------------
		
		-----------------Printing Artist Index-----------------
		Level 1 [Rick Astley]
		Level 2 [Earth, Wind & Fire | James Hype | Kaskade]
		Level 3 [AJ Tracey | David Guetta]
		Level 3 [Earth, Wind & Fire | Ed Sheeran]
		Level 3 [James Hype | Jason Derulo]
		Level 3 [Kaskade | Katy Perry | Master KG]
		Level 2 [The Black Eyed Peas]
		Level 3 [Rick Astley | Sabaton]
		Level 3 [The Black Eyed Peas | Tomaso Antonio Vitali]
		
		-----------------Printing Genre Index-----------------
		Level 1 [Disco | Hip Hop/Rap]
		Level 2 [Classical | Dance]
		Level 2 [Disco | Gospel-house]
		Level 2 [Hip Hop/Rap | Pop | Power Metal]
		
		
		-----------------select * from Music where Genre = 'Hip Hop/Rap'-----------------
		226678, Ladbroke Grove, AJ Tracey, Hip Hop/Rap, AJ Tracey
		
		-----------------select * from Music order by Artist-----------------
		226678, Ladbroke Grove, AJ Tracey, Hip Hop/Rap, AJ Tracey
		157456, She Wolf, David Guetta, Dance, Nothing but the Beat
		625751, Memories, David Guetta, Dance, One Love
		102254, September, Earth, Wind & Fire, Disco, September
		145821, Shape of You, Ed Sheeran, Pop, Divide
		111125, Afraid, James Hype, Dance, Single
		333658, In My Head, Jason Derulo, Pop, Jason Derülo
		965876, On Your Mind, Kaskade, Dance, Redux 003
		325482, Firework, Katy Perry, Pop, Teenage Dream
		914725, Jerusalema, Master KG, Gospel-house, Single
		188547, Never Gonna Give You Up, Rick Astley, Pop, Whenever You Need Somebody
		915412, The Lion From The North, Sabaton, Power Metal, Carolus Rex
		558521, Boom Boom Pow, The Black Eyed Peas, Dance, THE E.N.D
		656232, Chaconne in G Minor, Tomaso Antonio Vitali, Classical, Composition
		
		-----------------delete from Music where Genre = 'Pop'-----------------
		
		-----------------select * from Music-----------------
		914725, Jerusalema, Master KG, Gospel-house, Single
		965876, On Your Mind, Kaskade, Dance, Redux 003
		625751, Memories, David Guetta, Dance, One Love
		102254, September, Earth, Wind & Fire, Disco, September
		558521, Boom Boom Pow, The Black Eyed Peas, Dance, THE E.N.D
		656232, Chaconne in G Minor, Tomaso Antonio Vitali, Classical, Composition
		226678, Ladbroke Grove, AJ Tracey, Hip Hop/Rap, AJ Tracey
		915412, The Lion From The North, Sabaton, Power Metal, Carolus Rex
		157456, She Wolf, David Guetta, Dance, Nothing but the Beat
		111125, Afraid, James Hype, Dance, Single
		
		-----------------Printing Artist Index-----------------
		Level 1 [Rick Astley]
		Level 2 [Earth, Wind & Fire | James Hype | Kaskade]
		Level 3 [AJ Tracey | David Guetta]
		Level 3 [Earth, Wind & Fire]
		Level 3 [James Hype]
		Level 3 [Kaskade | Master KG]
		Level 2 [The Black Eyed Peas]
		Level 3 [Sabaton]
		Level 3 [The Black Eyed Peas | Tomaso Antonio Vitali]
		
		-----------------Printing Genre Index-----------------
		Level 1 [Disco | Hip Hop/Rap]
		Level 2 [Classical | Dance]
		Level 2 [Disco | Gospel-house]
		Level 2 [Hip Hop/Rap | Power Metal]
		
		
		-----------------delete from Music-----------------
		
		-----------------select * from Music-----------------
		Table is empty
		
		-----------------Printing Artist Index-----------------
		Level 1 []
		
		-----------------Printing Genre Index-----------------
		Level 1 []

		 */
	}
}