import { useEffect, useState } from "react";
import "./styles/MyPhotoCardList.css";
import { DataView } from "primereact/dataview";

const MyPhotoCardList = () => {
  // const [cards, setCards] = useState([]);
  const [products, setProducts] = useState([]);
  const [layout, setLayout] = useState("grid");

  var data = [
    {
      name: 1,
      image: "bamboo-watch.jpg",
    },
    {
      name: 2,
      image: "bamboo-watch.jpg",
    },
    {
      name: 3,
      image: "bamboo-watch.jpg",
    },
    {
      name: 4,
      image: "bamboo-watch.jpg",
    },
    {
      name: 5,
      image: "bamboo-watch.jpg",
    },
  ];

  useEffect(() => {
    setProducts(data);
  }, []);

  const itemTemplate = (product, layout) => {
    if (!product) {
      return;
    }

    if (layout === "list") return listItem(product);
    else if (layout === "grid") return gridItem(product);
  };

  const gridItem = (product) => {
    return (
      <div className="col-12 sm:col-6 lg:col-3 xl:col-3 p-2">
        <div className="p-4 border-1 surface-border surface-card border-round">
          <div className="flex flex-column align-items-center gap-3 py-5">
            <img
              src={`https://primefaces.org/cdn/primereact/images/product/${product.image}`}
              alt={product.name}
            />
          </div>
        </div>
      </div>
    );
  };

  return (
    <div>
      <div className="page-title">My Photo Card</div>
      <div className="card-container mt-3">
        <DataView
          value={products}
          itemTemplate={itemTemplate}
          layout={layout}
        />
      </div>
    </div>
  );
};

export default MyPhotoCardList;
