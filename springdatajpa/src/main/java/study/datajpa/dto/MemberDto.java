package study.datajpa.dto;

import lombok.Data;

@Data
public class MemberDto {
    private Long id;
    private String username;
    private String teamName;

    public MemberDto(Long id, String username, String teamName) {
        this.id = id;
        this.username = username;
        this.teamName = teamName;
    }

    @Override
    public String toString() {
        return "MemberDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", teamName='" + teamName + '\'' +
                '}';
    }
}
