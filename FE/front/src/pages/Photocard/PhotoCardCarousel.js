import React, { useState, useEffect } from 'react';
import { Carousel } from 'primereact/carousel';
import { Card } from "primereact/card";

export default function PhotoCardCarousel(props) {

    const [products, setProducts] = useState([]);

    useEffect(() => {
        console.log(props.photocards);
        setProducts(props.photocards)
    }, []);

    const productTemplate = (product) => {
        return (
            <div className="border-1 surface-border border-round m-2 text-center py-5 px-3">
                <div className="mb-3">
                    {/* <img src={`https://primefaces.org/cdn/primereact/images/product/${product.image}`} alt={product.name} className="w-6 shadow-2" /> */}
                    <h2>{product.photocardUrl}</h2>
                </div>
                <div>
                    <Card className="photocard-quantity" header={product.photocardQuantity} />
                </div>
            </div>
        );
    };

    return (
        <div className="card">
            <Carousel value={products} numVisible={3} numScroll={3} itemTemplate={productTemplate} />
        </div>
    )
}

