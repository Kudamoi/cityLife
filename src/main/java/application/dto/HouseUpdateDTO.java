package application.dto;

import application.enums.HouseUpdateAction;
import lombok.Getter;

@Getter
public class HouseUpdateDTO {
   HouseUpdateAction action;
   Long userId;
}
