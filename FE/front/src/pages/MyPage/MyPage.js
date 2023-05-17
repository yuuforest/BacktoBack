import axios from "axios";
import MyPhotoCardList from "./components/MyPhotoCardList";
import Profile from "./components/Profile";
import { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import { selectMemberId } from "store/reducers/loginReducer";

const MyPage = () => {
  //redux 데이터
  // const memberSeq = useSelector(selectMemberId);

  const [cards, setCards] = useState([]);
  const [quantity, setQuantity] = useState(0);

  const [memberSeq, setMemberSeq] = useState(1);

  const getMyPhotoCards = async () => {
    try {
      await axios
        .get(
          "http://k8a708.p.ssafy.io/api/point/photocard/getMyPhotocard/" +
            memberSeq
        )
        .then((response) => {
          console.log(response.data);
          setCards(response.data);
          setQuantity(cards.size());
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
