import { Carousel } from "primereact/carousel";
import { useEffect, useState } from "react";
import { Button } from "primereact/button";
import { Link } from "react-router-dom";
import "primeicons/primeicons.css";
import "./Main.css";

const Main = () => {
  const [url1, setUrl1] = useState("");
  const [url2, setUrl2] = useState("");
  const [url3, setUrl3] = useState("");

  useEffect(() => {
    setUrl1(process.env.PUBLIC_URL + "/main/main1.jpg");
    setUrl2(process.env.PUBLIC_URL + "/main/main2.jpg");
    setUrl3(process.env.PUBLIC_URL + "/main/main3.jpg");
  }, []);

  const images = [
    {
      number: 1,
      url: url1,
    },
    {
      number: 2,
      url: url2,
    },
    {
      number: 3,
      url: url3,
    },
  ];

  const responsiveOptions = [
    {
      breakpoint: "1199px",
      numVisible: 1,
      numScroll: 1,
    },
    {
      breakpoint: "991px",
      numVisible: 2,
      numScroll: 1,
    },
    {
      breakpoint: "767px",
      numVisible: 1,
      numScroll: 1,
    },
  ];

  const imageTemplate = (image) => {
    return (
      <div
        style={{ position: "relative", height: "100vh", overflow: "hidden" }}
      >
        <img
          src={image.url}
          style={{
            position: "absolute",
            top: "50%",
            left: "50%",
            height: "100%",
            width: "auto",
            transform: "translate(-50%, -50%)",
            minWidth: "100%",
            minHeight: "100%",
            objectFit: "cover",
          }}
          alt="carousel"
        ></img>
      </div>
    );
  };

  return (
    <div>
      <Carousel
        value={images}
        numVisible={1}
        numScroll={1}
        responsiveOptions={responsiveOptions}
        className="custom-carousel"
        circular
        autoplayInterval={5000}
        itemTemplate={imageTemplate}
        showNavButtons={false}
        showIndicators={false}
      />
      <Button className="overlay-button-1" icon="pi pi-video">
        <Link to="/live" style={{ textDecoration: "none", color: "inherit" }}>
          오늘의 경기 관람
        </Link>
      </Button>
      <Button className="overlay-button-2" icon="pi pi-cart-plus">
        <Link
          to="/photocard/home"
          style={{ textDecoration: "none", color: "inherit" }}
        >
          하이라이트 구매
        </Link>
      </Button>
    </div>
  );
};
export default Main;
