package si.skrjanec.example.data;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("data")
public class DataResource {

	@Inject
	private DataService dataBean;

	@GET
	public Response getAllData() {
		List<Data> data = dataBean.getAllData();
		return Response.ok(data).build();
	}
	
	@POST
    public Response addData(Data data) {
		dataBean.saveData(data);
        return Response.noContent().build();
    }
}