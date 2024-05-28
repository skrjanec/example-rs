package si.skrjanec.example.data;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

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
}