package in.co.vsys.myssksamaj.model.responses;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @author abhijeetjadhav
 */
public class BasicRegistrationResponse extends Entity {

    private List<MemberIdClass> data;

    public class MemberIdClass implements Serializable {
        private String MemberId;

        public String getMemberId() {
            return MemberId;
        }
    }

    public List<MemberIdClass> getData() {
        return data;
    }
}
