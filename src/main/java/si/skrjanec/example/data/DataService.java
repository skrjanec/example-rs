package si.skrjanec.example.data;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Session;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

@RequestScoped
public class DataService {

	@PersistenceContext(unitName = "example-jpa")
	private EntityManager em;

	public List<Data> getAllData() {
		return em.createNamedQuery("Data.findAll", Data.class).getResultList();
	}

	@Transactional
	public void saveData(Data data) {
		if (data != null) {
			em.persist(data);
		}
	}

	/*
	 How to speed up writing to DB?

	 1. Do it in parallel and async for each event. Data acquisition, sorting and
	 write. Via queue or some other mechanism.

	 2. If this data is coming in as a stream. Shouldn't the events already be
	 sorted? Maybe not in every case, but in general. What is the maximum amount
	 of time an event is expected to arrive? This can help us in setting the wait
	 time until events are sorted and written to db.

	 3. Batch insert (implemented)
	 */
	@Transactional
	public void process() throws FileNotFoundException {
		List<Data> dataList = this.readFile();

		// TODO: delete time logs someday
//		long b1 = System.currentTimeMillis();

		// TODO: ask maybe any other sorting needs to be done here?
		// time complexity O(n log n), < 0.5s for 300k records. looks ok
		Collections.sort(dataList, (s1, s2) -> {
			return (s1.getMatchId() + s1.getMarketId()).compareTo(s2.getMatchId() + s2.getMarketId());
		});

//		System.out.println("sorting time: " + (System.currentTimeMillis() - b1) + " ms");
		
		// should be done via properties
		em.unwrap(Session.class).setJdbcBatchSize(100);

//		long b2 = System.currentTimeMillis();

		for (int i = 0; i < dataList.size(); i++) {
			// TODO: delete logs some day
//			if (i % 10000 == 0) {
//				System.out.println(i);
//			}

			this.saveData(dataList.get(i));
		}

//		System.out.println("straight up writing time: " + (System.currentTimeMillis() - b2) + " ms");

	}

	private List<Data> readFile() throws FileNotFoundException {
		List<Data> results = new ArrayList<Data>();

		Scanner reader = null;

		try {
			File file = new File("fo_random.txt");
			reader = new Scanner(file);

			// skip first line
			reader.nextLine();

			while (reader.hasNextLine()) {
				String line = reader.nextLine().replace("'", "");
				String[] splitted = line.split("\\|");
				results.add(new Data(splitted[0], Integer.valueOf(splitted[1]), splitted[2], splitted.length == 4 ? splitted[3] : null));
			}

		} catch (FileNotFoundException e) {
			throw e;
		} finally {
			if (reader != null)
				reader.close();
		}

		return results;
	}
}