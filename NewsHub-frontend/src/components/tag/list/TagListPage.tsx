// import React, { useEffect, useState } from "react";
// import { Button, Col, Row } from "antd";
// import { Link, useSearchParams } from "react-router-dom";
// import { ITagSearch, IGetTags } from "../type.ts"; // Переконайтеся, що шлях до файлу типу правильний
// import http_common from "../../../http_common.ts";
// import TagCard from "../../../views/Home/TagCard.tsx";

import React, { useEffect, useState } from "react";
import { Button, Col, Form, Input, Pagination, Row } from "antd";
import { Link, useSearchParams } from "react-router-dom";
import { ITagSearch, IGetTags } from "../type"; // Переконайтеся, що шлях до вашого файлу з типами правильний
import http_common from "../../../http_common";
import TagCard from "../../../views/Home/TagCard.tsx";

const TagListPage = () => {
    const [data, setData] = useState<IGetTags>({
        content: [],
        totalPages: 0,
        totalElements: 0,
        number: 0,
    });

    const [searchParams, setSearchParams] = useSearchParams();

    const [formParams, setFormParams] = useState<ITagSearch>({
        name: searchParams.get('name') || "",
        page: Number(searchParams.get('page')) || 1,
        size: Number(searchParams.get('size')) || 1, 
    });

    const [form] = Form.useForm<ITagSearch>();

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await http_common.get<IGetTags>("/api/tags/search", {
                    params: {
                        ...formParams,
                        page: formParams.page - 1
                    }
                });
                setData(response.data);
            } catch (error) {
                console.error('Error fetching tags:', error);
            }
        };
        fetchData();
    }, [formParams]);

    const handleDelete = async (tagId: number) => {
        try {
            await http_common.delete(`/api/tags/${tagId}`);
            setData({ ...data, content: data.content.filter(x => x.id !== tagId)});
        } catch (error) {
            console.error(`Error: ${error}`);
        }
    };

    const handlePageChange = (page: number, newPageSize: number) => {
        setFormParams({...formParams, page, size: newPageSize});
        updateSearchParams({...formParams, page, size: newPageSize});
    };

    const updateSearchParams = (params: ITagSearch) => {
        for (const [key, value] of Object.entries(params)) {
            if (value !== undefined && value !== 0 && value !== "") {
                searchParams.set(key, value.toString());
            } else {
                searchParams.delete(key);
            }
        }
        setSearchParams(searchParams, { replace: true });
    };

    return (
        <>
            <h1>Список тегів</h1>
            <Link to="/tag/create">
                <Button type="primary" style={{ margin: "5px" }}>
                    Додати тег +
                </Button>
            </Link>

            <Row gutter={16}>
                <Col span={24}>
                    <Form form={form} onFinish={(values) => setFormParams({ ...formParams, ...values, page: 1 })} layout="vertical">
                        <Form.Item label="Назва" name="name" style={{ marginBottom: 0 }}>
                            <Input placeholder="Введіть назву для пошуку" />
                        </Form.Item>
                        <Row justify="center" gutter={16}>
                            <Col>
                                <Button type="primary" htmlType="submit">
                                    Пошук
                                </Button>
                            </Col>
                            <Col>
                                <Button onClick={() => form.resetFields()}>
                                    Скасувати
                                </Button>
                            </Col>
                        </Row>
                    </Form>
                </Col>
            </Row>

            <Row gutter={16}>
                {data.content.length > 0 ? (
                    data.content.map((tag) => <TagCard key={tag.id} item={tag} handleDelete={handleDelete} />)
                ) : (
                    <Col span={24}>Немає тегів для відображення.</Col>
                )}
            </Row>

            <Row justify="center" style={{ marginTop: '24px', marginBottom: '24px' }}>
                <Pagination
                    current={formParams.page}
                    pageSize={formParams.size}
                    total={data.totalElements}
                    onChange={handlePageChange}
                    showSizeChanger
                />
            </Row>

        </>
    );
};

export default TagListPage;