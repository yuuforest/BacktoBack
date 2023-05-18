import axios from "axios";
import MyPhotoCardList from "./components/MyPhotoCardList";
import Profile from "./components/Profile";
import { useState, useEffect } from "react";
import { memberSeq } from "components/State/UserState";
import { useRecoilValue } from "recoil";

const MyPage = () => {
  //redux 데이터
  // const curMemberSeq = useRecoilValue(memberSeq);

  const [cards, setCards] = useState([]);
  const [quantity, setQuantity] = useState(0);

  const [curMemberSeq, setMemberSeq] = useState(1);

  const getMyPhotoCards = async () => {
    try {
      await axios
        .get(
          "http://k8a708.p.ssafy.io/api/point/photocard/getMyPhotocard/" +
            curMemberSeq
        )
        .then((response) => {
          console.log(response.data);
          setCards(response.data, () => {
            setQuantity(cards.length);
            console.log("quantity: ", quantity);
            console.log("cards", cards);
          });
        });
      console.log("Get Photo Card Success......");
    } catch (error) {
      console.log("Get Photo Card Error......");
    }
  };

  useEffect(() => {
    console.log("MyPage Render............");
    getMyPhotoCards();
  }, []);

  return (
    <div>
      <Profile cardCount={quantity} />
      <hr />
      <MyPhotoCardList cards={cards} />
    </div>
  );
};

export default MyPage;
